package com.mcf.relationship.controller.user.response;

import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.Data;
import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/4 22:01
 */
@Data
public class CurrentUserResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = 7512397120688071622L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户类型
     */
    private Integer userType;
}
