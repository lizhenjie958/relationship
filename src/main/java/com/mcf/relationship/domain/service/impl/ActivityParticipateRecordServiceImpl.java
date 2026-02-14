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
import com.mcf.relationship.infra.manager.RedissonManager;
import com.mcf.relationship.infra.mapper.ActivityParticipateRecordMapper;
import com.mcf.relationship.infra.model.ActivityParticipateRecordDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
public class ActivityParticipateRecordServiceImpl extends ServiceImpl<ActivityParticipateRecordMapper, ActivityParticipateRecordDO> implements ActivityParticipateRecordService {

    @Resource
    private ActivityManager activityManager;

    @Resource
    private ActivityParticipateRecordManager activityParticipateRecordManager;

    @Resource
    private RedissonManager redissonManager;

    @Override
    public ParticipateRecordResponse queryParticipateRecord(ParticipateRecordQueryRequest request) {
        AssertUtil.checkObjectNotNull(request.getActivityId(), "活动ID");
        ActivityParticipateRecordBO participateRecordBO = activityParticipateRecordManager.queryActivityParticipateRecord(request.getActivityId(), UserLoginContextUtil.getUserId());
        ParticipateRecordResponse response =  ActivityParticipateRecordConverter.bo2response(participateRecordBO);
        return response;
    }

    /**
     * 参与活动
     * 使用分布式锁防止重复参与
     * 使用事务保证数据一致性
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void participate(ParticipateActivityRequest request) {
        Long userId = UserLoginContextUtil.getUserId();
        Long activityId = request.getActivityId();
        String lockKey = "activity:participate:" + activityId + ":" + userId;

        log.info("用户参与活动: userId={}, activityId={}", userId, activityId);

        // 使用分布式锁防止并发重复参与
        boolean locked = redissonManager.tryLock(lockKey, 3L, 10L);
        if (!locked) {
            log.warn("获取参与活动锁失败，请稍后重试: userId={}, activityId={}", userId, activityId);
            throw new BizException(BizExceptionEnum.REDEEM_FAILED, "操作过于频繁，请稍后重试");
        }

        try {
            // 检查是否已参与
            ActivityParticipateRecordBO participateRecordBO = activityParticipateRecordManager
                .queryActivityParticipateRecord(activityId, userId);
            if (participateRecordBO != null) {
                log.warn("用户已参与活动: userId={}, activityId={}", userId, activityId);
                throw new BizException(BizExceptionEnum.HAS_PARTICIPATE_ACTIVITY);
            }

            // 检查活动是否存在
            ActivityBO activityBO = activityManager.getActivity(activityId);
            if (activityBO == null) {
                log.warn("活动不存在: activityId={}", activityId);
                throw new BizException(BizExceptionEnum.ACTIVITY_NOT_EXIST);
            }

            // 检查活动状态
            if (!ActivityStatusEnum.START.getStatus().equals(activityBO.getActivityStatus())) {
                log.warn("活动状态不可参与: activityId={}, status={}",
                    activityId, ActivityStatusEnum.getDesc(activityBO.getActivityStatus()));
                throw new BizException(BizExceptionEnum.ACTIVITY_STATUS_ERROR,
                    ActivityStatusEnum.getDesc(activityBO.getActivityStatus()));
            }

            // 创建参与记录
            ActivityParticipateRecordBO participateRecord = new ActivityParticipateRecordBO();
            participateRecord.setParticipateStatus(ActivityParticipateStatusEnum.ONGOING.getStatus());
            participateRecord.setParticipateTime(LocalDateTime.now());
            participateRecord.setActivityId(activityId);
            participateRecord.setUserId(userId);
            participateRecord.setCurrentIndicator(0);

            activityParticipateRecordManager.participateActivity(participateRecord);
            log.info("用户参与活动成功: userId={}, activityId={}", userId, activityId);

        } finally {
            redissonManager.unlock(lockKey);
        }
    }
}
