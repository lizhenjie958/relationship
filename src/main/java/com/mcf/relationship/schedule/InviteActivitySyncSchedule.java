package com.mcf.relationship.schedule;

import com.mcf.relationship.common.annotations.DistributedTask;
import com.mcf.relationship.common.consts.CommonConst;
import com.mcf.relationship.common.enums.ActivityParticipateStatusEnum;
import com.mcf.relationship.common.enums.MemberAccessChannelEnum;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.activity.request.ParticipateRecordQueryRequest;
import com.mcf.relationship.domain.entity.ActivityBO;
import com.mcf.relationship.domain.entity.ActivityParticipateRecordBO;
import com.mcf.relationship.infra.manager.ActivityManager;
import com.mcf.relationship.infra.manager.ActivityParticipateRecordManager;
import com.mcf.relationship.infra.manager.UserManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 答题活动同步，完成后次日凌晨结算
 *
 * @Author ZhuPo
 * @date 2026/2/7 17:57
 */
@Component
public class InviteActivitySyncSchedule {

    @Resource
    private ActivityManager activityManager;

    @Resource
    private ActivityParticipateRecordManager activityParticipateRecordManager;

    @Resource
    private UserManager userManager;

    /**
     * 指标同步
     */
    @Scheduled(cron = "0 */10 * * * ?")
    @DistributedTask(name = "answerActivitySyncSchedule#syncStatus")
    public void syncStatus(){
        ActivityBO activityBO = activityManager.queryCurrentActivity(MemberAccessChannelEnum.INVITE_USER_ROLLUP.getCode());
        if(activityBO == null){
            return;
        }
        ParticipateRecordQueryRequest request = new ParticipateRecordQueryRequest();
        request.setPageSize(50);
        request.setActivityId(activityBO.getId());
        for (int i = 1; i <= CommonConst.MAX_BATCH; i++) {
            request.setPageNo(i);
            PageResponse<ActivityParticipateRecordBO> participateRecords = activityParticipateRecordManager.queryList(request);
            if(CollectionUtils.isEmpty(participateRecords.getList())){
                return;
            }
            for (ActivityParticipateRecordBO recordBO : participateRecords.getList()) {
                syncProgress(activityBO,recordBO);
            }
        }
    }


    /**
     * 同步进度
     * @param recordBO
     */
    private void syncProgress(ActivityBO activityBO, ActivityParticipateRecordBO recordBO){
        LocalDateTime startTime = activityBO.getStartDate().atStartOfDay();
        LocalDateTime endTime = activityBO.getEndDate().atStartOfDay().plusDays(1);
        int count = userManager.statisticsCount(recordBO.getUserId(),startTime,endTime);
        if(recordBO.getCurrentIndicator() != count){
            activityParticipateRecordManager.updateCurrentIndicator(recordBO.getId(), count);
            if(count >= recordBO.getCurrentIndicator() && ActivityParticipateStatusEnum.ONGOING.getStatus().equals(recordBO.getParticipateStatus())){
                activityParticipateRecordManager.changeToComplete(recordBO.getId());
            }
        }
    }
}
