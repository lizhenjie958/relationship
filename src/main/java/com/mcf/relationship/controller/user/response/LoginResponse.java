package com.mcf.relationship.controller.user.response;

import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/4 20:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = -8902919979712365165L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 登录Token
     */
    private String token;
}
