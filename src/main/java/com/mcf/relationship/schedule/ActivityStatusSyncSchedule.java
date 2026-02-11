package com.mcf.relationship.schedule;

/**
 *
 * 邀请用户注册结算
 * 活动期间，每日统计一次，跳过已经通过好友邀请获得会员的用户
 *
 * @Author ZhuPo
 * @date 2026/2/7 17:44
 */

import com.mcf.relationship.common.annotations.DistributedTask;
import com.mcf.relationship.common.consts.CommonConst;
import com.mcf.relationship.common.enums.ActivityParticipateStatusEnum;
import com.mcf.relationship.common.enums.ActivityStatusEnum;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.activity.request.ParticipateRecordQueryRequest;
import com.mcf.relationship.domain.entity.ActivityBO;
import com.mcf.relationship.domain.entity.ActivityParticipateRecordBO;
import com.mcf.relationship.infra.manager.ActivityManager;
import com.mcf.relationship.infra.manager.ActivityParticipateRecordManager;
import com.mcf.relationship.infra.manager.MemberManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

@Component
public class ActivityStatusSyncSchedule {

    @Resource
    private ActivityManager activityManager;

    @Resource
    private ActivityParticipateRecordManager activityParticipateRecordManager;

    @Resource
    private MemberManager memberManager;

    /**
     * 状态同步
     */
    @Scheduled(cron = "0 * 1,3,5 * * ?")
    @DistributedTask(name = "activityStatusSyncSchedule#status")
    public void status(){
        activityManager.changeStatus();
    }

    /**
     * 结算同步
     */
    @Scheduled(cron = "0 10 * * * ?")
    @DistributedTask(name = "activityStatusSyncSchedule#completeActivity")
    public void completeActivity(){
        ParticipateRecordQueryRequest request = new ParticipateRecordQueryRequest();
        request.setParticipateStatus(ActivityParticipateStatusEnum.COMPLETED.getStatus());
        request.setPageSize(100);
        for (int i = 0; i < CommonConst.MAX_BATCH; i++) {
            PageResponse<ActivityParticipateRecordBO> response = activityParticipateRecordManager.queryList(request);
            if(CollectionUtils.isEmpty(response.getList())){
                return;
            }

            for (ActivityParticipateRecordBO recordBO : response.getList()) {
                ActivityBO activityBO = activityManager.getActivity(recordBO.getActivityId());
                if(activityBO == null || !ActivityStatusEnum.START.getStatus().equals(activityBO.getActivityStatus())){
                    activityParticipateRecordManager.changeToInvalid(recordBO.getId());
                    continue;
                }
                boolean result = activityParticipateRecordManager.changeToSettle(recordBO.getId());
                if(result){
                    memberManager.recharge(recordBO.getUserId(), recordBO.getActivityId(),recordBO.getId() + "");
                }
            }
        }
    }

}
