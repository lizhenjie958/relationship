package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mcf.relationship.common.consts.CommonConst;
import com.mcf.relationship.common.consts.NumberConst;
import com.mcf.relationship.common.enums.AnswerStatusEnum;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.controller.answerpaper.request.AnswerPaperQueryRequest;
import com.mcf.relationship.domain.convert.AnswerPaperConverter;
import com.mcf.relationship.domain.entity.AnswerPaperBO;
import com.mcf.relationship.infra.mapper.AnswerPaperMapper;
import com.mcf.relationship.infra.model.AnswerPaperDO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhuPo
 * @date 2026/2/4 14:01
 */
@Component
public class AnswerPaperManager {

    @Resource
    private AnswerPaperMapper answerPaperMapper;

    /**
     * 保存
     * @param answerPaperBO
     */
    public Long save(AnswerPaperBO answerPaperBO){
        AnswerPaperDO answerPaperDO = AnswerPaperConverter.boToDo(answerPaperBO);
        answerPaperMapper.insert(answerPaperDO);
        return answerPaperDO.getId();
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    public AnswerPaperBO queryDetail(Long id){
        AssertUtil.checkObjectNotNull(id, "答卷ID");
        AnswerPaperDO answerPaperDO = answerPaperMapper.selectById(id);
        AssertUtil.checkDataExist(answerPaperDO, "答卷");
        return AnswerPaperConverter.doToBo(answerPaperDO);
    }

    /**
     * 查询列表
     * @param request
     * @return
     */
    public PageResponse<AnswerPaperBO> queryList(AnswerPaperQueryRequest request){
        LambdaQueryWrapper<AnswerPaperDO> queryWrapper = this.buildQuery(request);
        Page<AnswerPaperDO> answerPaperDOPage = answerPaperMapper.selectPage(request.page(), queryWrapper);
        return PageConvertUtil.convertPage(answerPaperDOPage, AnswerPaperConverter::doToBo);
    }

    /**
     * 修改为超时
     */
    public void changeToTimeout(){
        for (int i = 0; i < NumberConst.HUNDRED; i++) {
            LambdaQueryWrapper<AnswerPaperDO> updateWrapper = new LambdaQueryWrapper<AnswerPaperDO>()
                    .eq(AnswerPaperDO::getAnswerStatus, AnswerStatusEnum.ANSWERING.getStatus())
                    .le(AnswerPaperDO::getTimeoutTime, LocalDateTime.now())
                    .last(CommonConst.DEFAULT_BATCH);
            Long count = answerPaperMapper.selectCount(updateWrapper);
            if(count <= 0){
                return;
            }
            AnswerPaperDO updateDO = new AnswerPaperDO().setAnswerStatus(AnswerStatusEnum.TIMED_OUT.getStatus());
            answerPaperMapper.update(updateDO, updateWrapper);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 修改
     * @param answerPaperBO
     */
    public void updateById(AnswerPaperBO answerPaperBO){
        AnswerPaperDO answerPaperDO = AnswerPaperConverter.boToDo(answerPaperBO);
        answerPaperMapper.updateById(answerPaperDO);
    }

    /**
     * 查询最近一次正在进行的答卷，按照过期时间倒叙排列
     * @return
     */
    public AnswerPaperBO queryLatestAnswering(Long answerId){
        AssertUtil.checkObjectNotNull(answerId, "答题用户ID");
        LambdaQueryWrapper<AnswerPaperDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AnswerPaperDO::getAnswerId, answerId);
        queryWrapper.eq(AnswerPaperDO::getAnswerStatus, AnswerStatusEnum.ANSWERING.getStatus());
        queryWrapper.gt(AnswerPaperDO::getTimeoutTime, LocalDateTime.now());
        queryWrapper.orderByDesc(AnswerPaperDO::getTimeoutTime);
        AnswerPaperDO answerPaperDO = answerPaperMapper.selectOne(queryWrapper, false);
        return AnswerPaperConverter.doToBo(answerPaperDO);
    }


    public Long count(AnswerPaperQueryRequest request){
        LambdaQueryWrapper<AnswerPaperDO> queryWrapper = this.buildQuery(request);
        return answerPaperMapper.selectCount(queryWrapper);
    }


    private LambdaQueryWrapper<AnswerPaperDO> buildQuery(AnswerPaperQueryRequest request) {
        LambdaQueryWrapper<AnswerPaperDO> queryWrapper = new LambdaQueryWrapper<>();
        if(request.getUserId() != null){
            queryWrapper.eq(AnswerPaperDO::getAnswerId, request.getUserId());
        }
        if (request.getAnswerStatus() != null){
            queryWrapper.eq(AnswerPaperDO::getAnswerStatus, request.getAnswerStatus());
        }else if(CollectionUtils.isNotEmpty(request.getAnswerStatusList())){
            queryWrapper.in(AnswerPaperDO::getAnswerStatus, request.getAnswerStatusList());
        }
        if(request.getStartTime() != null){
            queryWrapper.ge(AnswerPaperDO::getCreateTime, request.getStartTime());
        }
        if(request.getEndTime() != null){
            queryWrapper.le(AnswerPaperDO::getCreateTime, request.getEndTime());
        }
        if(request.getExaminerId() != null){
            queryWrapper.eq(AnswerPaperDO::getExaminerId, request.getExaminerId());
        }
        return queryWrapper;
    }



}
