package com.mcf.relationship.domain.context;

import com.mcf.relationship.controller.member.request.RedeemMemberRequest;
import com.mcf.relationship.domain.entity.ActivityBO;
import com.mcf.relationship.domain.entity.MemberRedeemBO;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/8 17:25
 */
@Data
public class RedeemMemberRequestContext extends RedeemMemberRequest {
    @Serial
    private static final long serialVersionUID = -4695814663703373525L;

    /**
     * 兑换码信息
     */
    private MemberRedeemBO memberRedeemBO;

    /**
     * 活动信息
     */
    private ActivityBO activityBO;

}
