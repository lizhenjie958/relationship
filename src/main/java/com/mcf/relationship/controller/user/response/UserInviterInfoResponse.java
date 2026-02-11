package com.mcf.relationship.controller.user.response;

import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.Data;

import java.io.Serial;

/**
 * @author ZhuPo
 * @date 2026/2/11 9:20
 */
@Data
public class UserInviterInfoResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = -1880277975977683610L;

    /**
     * 邀请人ID
     */
    private Long inviterId;
    /**
     * 邀请人姓名
     */
    private String inviterName;
    /**
     * 邀请人的邀请码
     */
    private String inviteCode;
}
