package com.mcf.relationship.controller.user.request;

import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;
import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/7 15:27
 */
@Data
public class MaintainInviterRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = -7870337425806675615L;
    /**
     * 邀请码
     */
    private String inviteCode;
}
