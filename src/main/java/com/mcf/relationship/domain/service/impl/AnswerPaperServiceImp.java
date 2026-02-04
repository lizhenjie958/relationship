package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.enums.AnswerStatusEnum;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.controller.answerpaper.request.AnswerPaperQueryRequest;
import com.mcf.relationship.controller.answerpaper.request.CompleteAnswerPaperRequest;
import com.mcf.relationship.controller.answerpaper.response.AnswerPaperDetailResponse;
import com.mcf.relationship.controller.answerpaper.vo.SimpleAnswerPaperVO;
import com.mcf.relationship.domain.convert.AnswerPaperConverter;
import com.mcf.relationship.domain.entity.AnswerPaperBO;
import com.mcf.relationship.domain.entity.ExamPaperBO;
import com.mcf.relationship.domain.service.AnswerPaperService;
import com.mcf.relationship.infra.manager.AnswerPaperManager;
import com.mcf.relationship.infra.manager.ExamPaperManager;
import com.mcf.relationship.infra.mapper.AnswerPaperMapper;
import com.mcf.relationship.infra.model.AnswerPaperDO;
import org.springframework.stereotype.Service;

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
public class AnswerPaperServiceImp extends ServiceImpl<AnswerPaperMapper, AnswerPaperDO> implements AnswerPaperService {

    @Resource
    private AnswerPaperManager answerPaperManager;

    @Resource
    private ExamPaperManager examPaperManager;

    @Override
    public PageResponse<SimpleAnswerPaperVO> queryList(AnswerPaperQueryRequest request) {
        PageResponse<AnswerPaperBO> response = answerPaperManager.queryList(request);
        return PageConvertUtil.convertResponse(response, AnswerPaperConverter::bo2vo);
    }

    @Override
    public AnswerPaperDetailResponse queryDetail(Long id) {
        AssertUtil.checkObjectNotNull(id, "答卷ID");
        AnswerPaperBO answerPaperBO = answerPaperManager.queryDetail(id);
        return AnswerPaperConverter.bo2detail(answerPaperBO);
    }

    @Override
    public Integer completeAnswer(CompleteAnswerPaperRequest request) {
        AssertUtil.checkObjectNotNull(request.getId(),"答卷ID");
        AssertUtil.checkCollectionNotEmpty(request.getAnswerQuestionDTOList(), "用户答案");
        AnswerPaperBO answerPaperBO = answerPaperManager.queryDetail(request.getId());

        if(!AnswerStatusEnum.ANSWERING.getStatus().equals(answerPaperBO.getAnswerStatus())){
            throw new BizException(BizExceptionEnum.ANSWER_PAPER_STATUS_ERROR,AnswerStatusEnum.getDesc(answerPaperBO.getAnswerStatus()),"不可作答");
        }
        if(answerPaperBO.getTimeoutTime().isBefore(LocalDateTime.now())){
            throw new BizException(BizExceptionEnum.ANSWER_PAPER_STATUS_ERROR,AnswerStatusEnum.TIMED_OUT.getDesc(),"不可作答");
        }
        ExamPaperBO examPaperBO = examPaperManager.queryDetail(answerPaperBO.getExamPaperId());

        answerPaperBO.setAnswerQuestionDTOList(request.getAnswerQuestionDTOList());
        AnswerPaperBO updateBO = answerPaperBO.completeAnswerPaper(examPaperBO);
        answerPaperManager.updateById(updateBO);
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
}
