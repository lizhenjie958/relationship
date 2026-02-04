package com.mcf.relationship.controller.user.request;

import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/4 20:13
 */
@Data
public class LoginByWxRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = -1627795653228969833L;
    /**
     * 邀请人id
     */
    private Long inviterId;
    /**
     * 微信授权码
     */
    private String openIdAuthCode;
}
