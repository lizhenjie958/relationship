package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.enums.ActivityParticipateStatusEnum;
import com.mcf.relationship.common.enums.ActivityStatusEnum;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.activity.request.ParticipateActivityRequest;
import com.mcf.relationship.controller.activity.request.ParticipateRecordQueryRequest;
import com.mcf.relationship.controller.activity.response.ParticipateRecordResponse;
import com.mcf.relationship.domain.convert.ActivityParticipateRecordConverter;
import com.mcf.relationship.domain.entity.ActivityBO;
import com.mcf.relationship.domain.entity.ActivityParticipateRecordBO;
import com.mcf.relationship.domain.service.ActivityParticipateRecordService;
import com.mcf.relationship.infra.manager.ActivityManager;
import com.mcf.relationship.infra.manager.ActivityParticipateRecordManager;
import com.mcf.relationship.infra.mapper.ActivityParticipateRecordMapper;
import com.mcf.relationship.infra.model.ActivityParticipateRecordDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-07
 */
@Service
public class ActivityParticipateRecordServiceImpl extends ServiceImpl<ActivityParticipateRecordMapper, ActivityParticipateRecordDO> implements ActivityParticipateRecordService {

    @Resource
    private ActivityManager activityManager;

    @Resource
    private ActivityParticipateRecordManager activityParticipateRecordManager;

    @Override
    public ParticipateRecordResponse queryParticipateRecord(ParticipateRecordQueryRequest request) {
        AssertUtil.checkObjectNotNull(request.getActivityId(), "活动ID");
        ActivityParticipateRecordBO participateRecordBO = activityParticipateRecordManager.queryActivityParticipateRecord(request.getActivityId(), UserLoginContextUtil.getUserId());
        ParticipateRecordResponse response =  ActivityParticipateRecordConverter.bo2response(participateRecordBO);
        return response;
    }

    @Override
    public void participate(ParticipateActivityRequest request) {
        ActivityParticipateRecordBO participateRecordBO = activityParticipateRecordManager.queryActivityParticipateRecord(request.getActivityId(), UserLoginContextUtil.getUserId());
        if(participateRecordBO != null){
            throw new BizException(BizExceptionEnum.HAS_PARTICIPATE_ACTIVITY);
        }
        ActivityBO activityBO = activityManager.getActivity(request.getActivityId());
        if (activityBO == null){
            throw new BizException(BizExceptionEnum.ACTIVITY_NOT_EXIST);
        }
        if (!ActivityStatusEnum.START.getStatus().equals(activityBO.getActivityStatus())){
            throw new BizException(BizExceptionEnum.ACTIVITY_STATUS_ERROR, ActivityStatusEnum.getDesc(activityBO.getActivityStatus()));
        }
        ActivityParticipateRecordBO participateRecord = new ActivityParticipateRecordBO();
        participateRecord.setParticipateStatus(ActivityParticipateStatusEnum.ONGOING.getStatus());
        participateRecord.setParticipateTime(LocalDateTime.now());
        participateRecord.setActivityId(request.getActivityId());
        participateRecord.setUserId(UserLoginContextUtil.getUserId());
        activityParticipateRecordManager.participateActivity(participateRecord);
    }
}
