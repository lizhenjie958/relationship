package com.mcf.relationship.domain.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.enums.AnswerStatusEnum;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.answerpaper.request.AnswerPaperQueryRequest;
import com.mcf.relationship.controller.answerpaper.request.CompleteAnswerPaperRequest;
import com.mcf.relationship.controller.answerpaper.request.QueryLatestAnsweringRequest;
import com.mcf.relationship.controller.answerpaper.response.AnswerPaperDetailResponse;
import com.mcf.relationship.controller.answerpaper.response.SimpleAnswerPaperResponse;
import com.mcf.relationship.controller.answerpaper.vo.AnswerPaperVO;
import com.mcf.relationship.domain.convert.AnswerPaperConverter;
import com.mcf.relationship.domain.entity.AnswerPaperBO;
import com.mcf.relationship.domain.entity.ExamPaperBO;
import com.mcf.relationship.domain.service.AnswerPaperService;
import com.mcf.relationship.infra.manager.AnswerPaperManager;
import com.mcf.relationship.infra.manager.ExamPaperManager;
import com.mcf.relationship.infra.mapper.AnswerPaperMapper;
import com.mcf.relationship.infra.model.AnswerPaperDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户答卷表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-04
 */
@Service
@Slf4j
public class AnswerPaperServiceImpl extends ServiceImpl<AnswerPaperMapper, AnswerPaperDO> implements AnswerPaperService {

    @Resource
    private AnswerPaperManager answerPaperManager;

    @Resource
    private ExamPaperManager examPaperManager;

    @Resource
    private AnswerPaperMapper answerPaperMapper;

    @Override
    public PageResponse<AnswerPaperVO> queryList(AnswerPaperQueryRequest request) {
        PageResponse<AnswerPaperBO> response = answerPaperManager.queryList(request);
        return PageConvertUtil.convertResponse(response, AnswerPaperConverter::bo2vo);
    }

    @Override
    public AnswerPaperDetailResponse queryDetail(Long id) {
        AssertUtil.checkObjectNotNull(id, "答卷ID");
        return AnswerPaperConverter.bo2detail(answerPaperManager.queryDetail(id));
    }

    /**
     * 完成答卷
     * 使用乐观锁防止并发提交
     * 使用事务保证数据一致性
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer completeAnswer(CompleteAnswerPaperRequest request) {
        Long answerPaperId = request.getId();
        AssertUtil.checkObjectNotNull(answerPaperId, "答卷ID");

        log.info("用户完成答卷: answerPaperId={}", answerPaperId);

        // 先查询答卷详情（用于计算分数）
        AnswerPaperBO answerPaperBO = answerPaperManager.queryDetail(answerPaperId);
        if (answerPaperBO == null) {
            log.warn("答卷不存在: answerPaperId={}", answerPaperId);
            throw new BizException(BizExceptionEnum.ANSWER_PAPER_STATUS_ERROR, "答卷不存在", "无法作答");
        }

        // 计算分数
        ExamPaperBO examPaperBO = examPaperManager.queryDetail(answerPaperBO.getExamPaperId());
        answerPaperBO.setAnswerQuestionDTOList(request.getAnswerQuestionDTOList());
        AnswerPaperBO updateBO = answerPaperBO.completeAnswerPaper(examPaperBO);

        // 使用乐观锁更新答卷状态（防止并发提交和超时问题）
        LocalDateTime now = LocalDateTime.now();
        LambdaUpdateWrapper<AnswerPaperDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AnswerPaperDO::getId, answerPaperId)
            .eq(AnswerPaperDO::getAnswerStatus, AnswerStatusEnum.ANSWERING.getStatus())
            .gt(AnswerPaperDO::getTimeoutTime, now)
            .set(AnswerPaperDO::getAnswers, JSONObject.toJSONString(request.getAnswerQuestionDTOList()))
            .set(AnswerPaperDO::getAnswerStatus, AnswerStatusEnum.COMPLETED.getStatus())
            .set(AnswerPaperDO::getScore, updateBO.getScore())
            .set(AnswerPaperDO::getCompleteTime, now);

        int updateResult = answerPaperMapper.update(null, updateWrapper);
        if (updateResult == 0) {
            // 更新失败，说明状态已改变或已超时
            AnswerPaperBO currentBO = answerPaperManager.queryDetail(answerPaperId);
            String statusDesc = AnswerStatusEnum.getDesc(currentBO.getAnswerStatus());

            log.warn("答卷状态已改变或已超时，提交失败: answerPaperId={}, status={}",
                answerPaperId, statusDesc);

            if (AnswerStatusEnum.TIMED_OUT.getStatus().equals(currentBO.getAnswerStatus())
                || currentBO.getTimeoutTime().isBefore(now)) {
                throw new BizException(BizExceptionEnum.ANSWER_PAPER_STATUS_ERROR,
                    AnswerStatusEnum.TIMED_OUT.getDesc(), "不可作答");
            } else {
                throw new BizException(BizExceptionEnum.ANSWER_PAPER_STATUS_ERROR, statusDesc, "不可作答");
            }
        }

        log.info("用户完成答卷成功: answerPaperId={}, score={}", answerPaperId, updateBO.getScore());
        return updateBO.getScore();
    }

    @Override
    public void giveUp(Long id) {
        AssertUtil.checkObjectNotNull(id,"答卷ID");
        AnswerPaperBO answerPaperBO = answerPaperManager.queryDetail(id);
        if(!AnswerStatusEnum.ANSWERING.getStatus().equals(answerPaperBO.getAnswerStatus())){
            throw new BizException(BizExceptionEnum.ANSWER_PAPER_STATUS_ERROR,AnswerStatusEnum.getDesc(answerPaperBO.getAnswerStatus()),"不可放弃");
        }
        answerPaperManager.updateById(answerPaperBO.giveUpAnswerPaper());
    }

    @Override
    public SimpleAnswerPaperResponse queryLatestAnswering(QueryLatestAnsweringRequest request) {
        AnswerPaperBO answerPaperBO = answerPaperManager.queryLatestAnswering(UserLoginContextUtil.getUserId());
        return AnswerPaperConverter.bo2LatestAnswering(answerPaperBO);
    }
}
