package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mcf.relationship.common.enums.ActivityStatusEnum;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.domain.convert.ActivityConverter;
import com.mcf.relationship.domain.convert.ActivityParticipateRecordConverter;
import com.mcf.relationship.domain.entity.ActivityBO;
import com.mcf.relationship.domain.entity.ActivityParticipateRecordBO;
import com.mcf.relationship.infra.mapper.ActivityMapper;
import com.mcf.relationship.infra.mapper.ActivityParticipateRecordMapper;
import com.mcf.relationship.infra.model.ActivityDO;
import com.mcf.relationship.infra.model.ActivityParticipateRecordDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @Author ZhuPo
 * @date 2026/2/8 08:58
 */
@Component
public class ActivityManager {

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ActivityParticipateRecordMapper activityParticipateRecordMapper;

    public ActivityBO getActivity(Long activityId){
        AssertUtil.checkObjectNotNull(activityId, "活动id");
        ActivityDO activityDO = activityMapper.selectById(activityId);
        return ActivityConverter.do2bo(activityDO);
    }

    /**
     * 查询当前活动
     * @param channelCode
     * @return
     */
    public ActivityBO queryCurrentActivity(String channelCode){
        LocalDate now = LocalDate.now();
        AssertUtil.checkStringNotBlank(channelCode, "活动类型");
        LambdaUpdateWrapper<ActivityDO> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(ActivityDO::getChannelCode, channelCode)
                .le(ActivityDO::getStartDate, now)
                .ge(ActivityDO::getEndDate, now)
                .eq(ActivityDO::getActivityStatus, ActivityStatusEnum.START.getStatus())
        ;
        ActivityDO activityDO = activityMapper.selectOne(queryWrapper, false);
        return ActivityConverter.do2bo(activityDO);
    }

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
}
