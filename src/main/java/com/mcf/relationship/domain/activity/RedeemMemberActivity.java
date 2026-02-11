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
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @Author ZhuPo
 * @date 2026/2/8 17:27
 */
@Component
public class RedeemMemberActivity {

    @Resource
    private MemberRedeemManager memberRedeemManager;

    @Resource
    private ActivityManager activityManager;

    @Resource
    private MemberManager memberManager;

    @Resource
    private MemberAccessRecordManager memberAccessRecordManager;


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
     */
    public void useRedeem(RedeemMemberRequestContext context) {
        boolean result = memberRedeemManager.useRedeem(UserLoginContextUtil.getUserId(), context.getRedeemCode());
        if(!result){
            throw new BizException(BizExceptionEnum.REDEEM_FAILED);
        }
    }

    /**
     * 充值会员
     * @param context
     */
    public void rechargeMember(RedeemMemberRequestContext context) {
        ActivityBO activityBO = context.getActivityBO();
        memberManager.recharge(UserLoginContextUtil.getUserId(), activityBO.getId(), context.getRedeemCode() );
    }

}
