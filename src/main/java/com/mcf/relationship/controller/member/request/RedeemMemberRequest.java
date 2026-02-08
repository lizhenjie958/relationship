package com.mcf.relationship.controller.member.request;

import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/7 23:07
 */
@Data
public class RedeemMemberRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = 8748209168847217751L;
    /**
     * 兑换码
     */
    private String redeemCode;
}
