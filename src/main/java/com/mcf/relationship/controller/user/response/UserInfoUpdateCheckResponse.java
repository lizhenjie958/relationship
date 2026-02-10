package com.mcf.relationship.controller.user.response;

import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/10 22:04
 */
@Data
public class UserInfoUpdateCheckResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = -3321638341826796998L;
    /**
     * 更新次数
     */
    private Integer updateTimes = 0;
    /**
     * 更新次数限制
     */
    private Integer updateTimesLimit = 5;
}
