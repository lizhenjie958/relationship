package com.mcf.relationship.domain.service;

import com.mcf.relationship.controller.member.request.RedeemMemberRequest;
import com.mcf.relationship.infra.model.MemberRedeemDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-08
 */
public interface MemberRedeemService extends IService<MemberRedeemDO> {

    /**
     * 兑换会员
     */
    void redeemMember(RedeemMemberRequest request);

    /**
     * 批量生成兑换码
     */
    void batchGenerateRedeem(Long activityId, Integer amount);
}
