package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mcf.relationship.common.enums.ActivityParticipateStatusEnum;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.controller.activity.request.ParticipateRecordQueryRequest;
import com.mcf.relationship.domain.convert.ActivityParticipateRecordConverter;
import com.mcf.relationship.domain.entity.ActivityParticipateRecordBO;
import com.mcf.relationship.infra.mapper.ActivityParticipateRecordMapper;
import com.mcf.relationship.infra.model.ActivityParticipateRecordDO;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author ZhuPo
 * @date 2026/2/11 13:56
 */
@Component
public class ActivityParticipateRecordManager {

    @Resource
    private ActivityParticipateRecordMapper activityParticipateRecordMapper;

    /**
     * 查询活动参与记录
     * @param activityId
     * @param userId
     * @return
     */
    public ActivityParticipateRecordBO queryActivityParticipateRecord(Long activityId, Long userId){
        AssertUtil.checkObjectNotNull(activityId, "活动id");
        AssertUtil.checkObjectNotNull(userId, "用户id");
        LambdaUpdateWrapper<ActivityParticipateRecordDO> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(ActivityParticipateRecordDO::getActivityId, activityId)
                .eq(ActivityParticipateRecordDO::getUserId, userId)
        ;
        ActivityParticipateRecordDO participateRecordDO = activityParticipateRecordMapper.selectOne(queryWrapper, false);
        return ActivityParticipateRecordConverter.do2bo(participateRecordDO);
    }

    /**
     * 参加活动
     * @param participateRecord
     */
    public void participateActivity(ActivityParticipateRecordBO participateRecord) {
        AssertUtil.checkObjectNotNull(participateRecord, "活动参与记录");
        AssertUtil.checkObjectNotNull(participateRecord.getActivityId(), "活动id");
        AssertUtil.checkObjectNotNull(participateRecord.getUserId(), "用户id");
        activityParticipateRecordMapper.insert(participateRecord);
    }

    /**
     * 查询记录
     * @param request
     * @return
     */
    public PageResponse<ActivityParticipateRecordBO> queryList(ParticipateRecordQueryRequest request){
        LambdaUpdateWrapper<ActivityParticipateRecordDO> queryWrapper = new LambdaUpdateWrapper<>();
        if(request.getActivityId() != null){
            queryWrapper.eq(ActivityParticipateRecordDO::getActivityId, request.getActivityId());
        }
        if(request.getParticipateStatus() != null){
            queryWrapper.eq(ActivityParticipateRecordDO :: getParticipateStatus, request.getParticipateStatus());
        }
        Page<ActivityParticipateRecordDO> page = activityParticipateRecordMapper.selectPage(request.page(), queryWrapper);
        return PageConvertUtil.convertPage(page, ActivityParticipateRecordConverter::do2bo);
    }


    /**
     * 更新当前指标
     * @param id
     * @param currentIndicator
     */
    public void updateCurrentIndicator(Long id, Integer currentIndicator){
        AssertUtil.checkObjectNotNull(id, "参与ID");
        AssertUtil.checkObjectNotNull(currentIndicator, "指标");
        ActivityParticipateRecordDO recordDO = new ActivityParticipateRecordDO();
        recordDO.setId(id);
        recordDO.setCurrentIndicator(currentIndicator);
        activityParticipateRecordMapper.updateById(recordDO);
    }

    /**
     * 更新状态至已完成
     * @param id
     */
    public void changeToComplete(Long id){
        ActivityParticipateRecordDO recordDO = new ActivityParticipateRecordDO();
        recordDO.setId(id);
        recordDO.setParticipateStatus(ActivityParticipateStatusEnum.COMPLETED.getStatus());
        recordDO.setCompleteTime(LocalDateTime.now());
        activityParticipateRecordMapper.updateById(recordDO);
    }

    public void changeToInvalid(Long id){
        ActivityParticipateRecordDO recordDO = new ActivityParticipateRecordDO();
        recordDO.setId(id);
        recordDO.setParticipateStatus(ActivityParticipateStatusEnum.INVALID.getStatus());
        recordDO.setInvalidTime(LocalDateTime.now());
        activityParticipateRecordMapper.updateById(recordDO);
    }

    /**
     * 状态变更已结算
     * @param id
     */
    public boolean changeToSettle(Long id){
        LambdaUpdateWrapper<ActivityParticipateRecordDO> updateWrapper = new LambdaUpdateWrapper<ActivityParticipateRecordDO>()
                .eq(ActivityParticipateRecordDO::getId, id)
                .eq(ActivityParticipateRecordDO::getParticipateStatus, ActivityParticipateStatusEnum.COMPLETED.getStatus())
                .set(ActivityParticipateRecordDO::getParticipateStatus, ActivityParticipateStatusEnum.SETTLE.getStatus())
                .set(ActivityParticipateRecordDO::getSettleTime, LocalDateTime.now());
        return activityParticipateRecordMapper.update(null, updateWrapper) > 0;
    }
}
