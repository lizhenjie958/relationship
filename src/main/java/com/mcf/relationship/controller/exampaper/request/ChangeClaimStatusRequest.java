package com.mcf.relationship.controller.exampaper.request;

import com.mcf.relationship.common.enums.ClaimStatusEnum;
import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/7 12:26
 */
@Data
public class ChangeClaimStatusRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = 3505184954202805107L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 开启领取状态
     * @see ClaimStatusEnum
     */
    private Integer claimStatus;
}
