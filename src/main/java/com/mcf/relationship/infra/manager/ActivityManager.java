package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mcf.relationship.common.enums.ActivityStatusEnum;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.domain.convert.ActivityConverter;
import com.mcf.relationship.domain.entity.ActivityBO;
import com.mcf.relationship.infra.mapper.ActivityMapper;
import com.mcf.relationship.infra.model.ActivityDO;
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
        return queryActivity(channelCode, LocalDate.now());
    }

    /**
     * 查询指定时间的活动
     * @param channelCode
     * @return
     */
    public ActivityBO queryActivityOnDate(String channelCode,LocalDate localDate){
        AssertUtil.checkObjectNotNull(localDate, "指定时间");
        return queryActivity(channelCode, localDate);
    }

    /**
     * 变更活动状态
     */
    public void changeStatus(){
        LocalDate now = LocalDate.now();
        LambdaUpdateWrapper<ActivityDO> changeStart = new LambdaUpdateWrapper<ActivityDO>()
                .le(ActivityDO::getStartDate, now)
                .ge(ActivityDO::getEndDate, now)
                .eq(ActivityDO::getActivityStatus, ActivityStatusEnum.NOT_STARTED.getStatus())
                .set(ActivityDO::getActivityStatus, ActivityStatusEnum.START.getStatus());
        activityMapper.update(null,changeStart);

        LambdaUpdateWrapper<ActivityDO> changeEnd = new LambdaUpdateWrapper<ActivityDO>()
                .le(ActivityDO::getEndDate, now)
                .eq(ActivityDO::getActivityStatus, ActivityStatusEnum.START.getStatus())
                .set(ActivityDO::getActivityStatus, ActivityStatusEnum.END.getStatus());
        activityMapper.update(null,changeEnd);
    }

    /**
     * 查询活动
     * @param channelCode
     * @param now
     * @return
     */
    public ActivityBO queryActivity(String channelCode, LocalDate now){
        if(now == null){
            now = LocalDate.now();
        }
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

}
