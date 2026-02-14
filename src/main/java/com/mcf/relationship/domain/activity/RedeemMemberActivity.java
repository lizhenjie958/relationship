package com.mcf.relationship.domain.activity;

import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.enums.RedeemStatusEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.CheckSumUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.domain.context.RedeemMemberRequestContext;
import com.mcf.relationship.domain.entity.ActivityBO;
import com.mcf.relationship.domain.entity.MemberRedeemBO;
import com.mcf.relationship.infra.manager.ActivityManager;
import com.mcf.relationship.infra.manager.MemberAccessRecordManager;
import com.mcf.relationship.infra.manager.MemberManager;
import com.mcf.relationship.infra.manager.MemberRedeemManager;
import com.mcf.relationship.infra.manager.RedissonManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @Author ZhuPo
 * @date 2026/2/8 17:27
 */
@Component
@Slf4j
public class RedeemMemberActivity {

    @Resource
    private MemberRedeemManager memberRedeemManager;

    @Resource
    private ActivityManager activityManager;

    @Resource
    private MemberManager memberManager;

    @Resource
    private MemberAccessRecordManager memberAccessRecordManager;

    @Resource
    private RedissonManager redissonManager;


    /**
     * 验证兑换码
     */
    public void checkRedeemCode(RedeemMemberRequestContext context) {
        AssertUtil.checkStringNotBlank(context.getRedeemCode(), "兑换码");
        if (!CheckSumUtil.isValidCode(context.getRedeemCode())) {
            throw new BizException(BizExceptionEnum.REDEEM_CODE_NOT_EXIST);
        }
    }

    /**
     * 验证兑换码状态
     */
    public void checkRedeemStatus(RedeemMemberRequestContext context) {
        MemberRedeemBO memberRedeemBO = memberRedeemManager.queryRedeem(context.getRedeemCode());
        if(memberRedeemBO == null){
            throw new BizException(BizExceptionEnum.REDEEM_CODE_NOT_EXIST);
        }
        if(!RedeemStatusEnum.WAIT_USE.getStatus().equals(memberRedeemBO.getRedeemStatus())){
            throw new BizException(BizExceptionEnum.REDEEM_CODE_STATUS_ERROR, RedeemStatusEnum.getDesc(memberRedeemBO.getRedeemStatus()));
        }
        context.setMemberRedeemBO(memberRedeemBO);
    }

    /**
     * 验证活动状态
     */
    public void checkActivity(RedeemMemberRequestContext context){
        Long activityId = context.getMemberRedeemBO().getActivityId();
        ActivityBO activityBO = activityManager.getActivity(activityId);
        if(activityBO == null){
            throw new BizException(BizExceptionEnum.ACTIVITY_NOT_EXIST);
        }
        LocalDate now = LocalDate.now();
        if (activityBO.getStartDate().isAfter(now)) {
            throw new BizException(BizExceptionEnum.ACTIVITY_NOT_START);
        }
        if (activityBO.getEndDate().isBefore(now)) {
            throw new BizException(BizExceptionEnum.ACTIVITY_ENDED);
        }
        context.setActivityBO(activityBO);
    }

    /**
     * 用户兑换
     * 使用分布式锁防止并发兑换同一兑换码
     * 使用事务保证兑换和充值的原子性
     */
    @Transactional(rollbackFor = Exception.class)
    public void useRedeem(RedeemMemberRequestContext context) {
        Long userId = UserLoginContextUtil.getUserId();
        String redeemCode = context.getRedeemCode();
        String lockKey = "redeem:use:" + redeemCode;

        log.info("用户兑换会员: userId={}, redeemCode={}", userId, redeemCode);

        // 使用分布式锁防止并发兑换同一兑换码
        boolean locked = redissonManager.tryLock(lockKey, 3L, 10L);
        if (!locked) {
            log.warn("获取兑换锁失败，请稍后重试: userId={}, redeemCode={}", userId, redeemCode);
            throw new BizException(BizExceptionEnum.REDEEM_FAILED, "操作过于频繁，请稍后重试");
        }

        try {
            // 标记兑换码为已使用
            boolean result = memberRedeemManager.useRedeem(userId, redeemCode);
            if (!result) {
                log.warn("兑换码使用失败: userId={}, redeemCode={}", userId, redeemCode);
                throw new BizException(BizExceptionEnum.REDEEM_FAILED);
            }

            log.info("兑换码使用成功，开始充值会员: userId={}, redeemCode={}", userId, redeemCode);

        } finally {
            redissonManager.unlock(lockKey);
        }
    }

    /**
     * 充值会员
     * 注意：此方法必须在事务中调用
     * @param context
     */
    public void rechargeMember(RedeemMemberRequestContext context) {
        Long userId = UserLoginContextUtil.getUserId();
        ActivityBO activityBO = context.getActivityBO();
        String redeemCode = context.getRedeemCode();

        log.info("充值会员: userId={}, activityId={}, redeemCode={}", userId, activityBO.getId(), redeemCode);

        memberManager.recharge(userId, activityBO.getId(), redeemCode);

        log.info("充值会员成功: userId={}, activityId={}", userId, activityBO.getId());
    }

}
