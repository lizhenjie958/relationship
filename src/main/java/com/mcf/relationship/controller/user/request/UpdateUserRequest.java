package com.mcf.relationship.controller.user.request;

import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/4 23:19
 */
@Data
public class UpdateUserRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = 6291481914424975410L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 头像
     */
    private String avatar;
}
