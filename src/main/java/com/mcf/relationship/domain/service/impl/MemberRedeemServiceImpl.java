package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.enums.MemberAccessChannelEnum;
import com.mcf.relationship.common.enums.RedeemStatusEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.common.util.CheckSumUtil;
import com.mcf.relationship.controller.member.request.RedeemMemberRequest;
import com.mcf.relationship.domain.activity.RedeemMemberActivity;
import com.mcf.relationship.domain.context.RedeemMemberRequestContext;
import com.mcf.relationship.domain.entity.ActivityBO;
import com.mcf.relationship.domain.service.MemberRedeemService;
import com.mcf.relationship.infra.manager.ActivityManager;
import com.mcf.relationship.infra.mapper.MemberRedeemMapper;
import com.mcf.relationship.infra.model.MemberRedeemDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-08
 */
@Service
public class MemberRedeemServiceImpl extends ServiceImpl<MemberRedeemMapper, MemberRedeemDO> implements MemberRedeemService {

    @Resource
    private RedeemMemberActivity redeemMemberActivity;

    @Resource
    private ActivityManager activityManager;

    @Override
    public void redeemMember(RedeemMemberRequest request) {
        RedeemMemberRequestContext context = BeanCopyUtil.copyForNew(request, new RedeemMemberRequestContext());
        redeemMemberActivity.checkRedeemCode(context);
        redeemMemberActivity.checkRedeemStatus( context);
        redeemMemberActivity.checkActivity( context);
        redeemMemberActivity.useRedeem(context);
        redeemMemberActivity.rechargeMember(context);
    }

    /**
     * 批量生成兑换码
     * @param amount
     * @param activityId
     */
    public void batchGenerateRedeem(Long activityId, Integer amount) {
        AssertUtil.checkObjectNotNull(amount, "生成数量");
        AssertUtil.checkObjectNotNull(activityId, "活动ID");
        ActivityBO activity = activityManager.getActivity(activityId);
        AssertUtil.checkDataExist(activity, "活动");
        if(!StringUtils.equals(activity.getChannelCode(), MemberAccessChannelEnum.REDEEM_MEMBER.getCode())){
            throw new BizException(BizExceptionEnum.ACTIVITY_NOT_SUPPORT_REDEEM_MEMBER);
        }
        int batchSize = 50; // 每批处理的数量
        for (int i = 0; i < amount; i += batchSize) {
            int end = Math.min(i + batchSize, amount); // 计算当前批次的结束位置
            List<MemberRedeemDO> batchList = new ArrayList<>();
            for (int j = i; j < end; j++) {
                MemberRedeemDO redeemDO = new MemberRedeemDO();
                redeemDO.setRedeemCode(CheckSumUtil.generateCode());
                redeemDO.setRedeemStatus(RedeemStatusEnum.WAIT_USE.getStatus());
                redeemDO.setActivityId(activityId);
                batchList.add(redeemDO);
            }
            this.saveBatch(batchList);
        }
    }
}
