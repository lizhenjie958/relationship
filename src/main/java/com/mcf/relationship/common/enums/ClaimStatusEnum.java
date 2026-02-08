package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 开启领取状态枚举
 *
 * @author ZhuPo
 * @date 2026/2/3 16:13
 */
@AllArgsConstructor
@Getter
public enum ClaimStatusEnum {
    NOT_CLAIMABLE(0,"未开启"),
    CLAIM_ALLOWED(1,"可领取"),
    CLAIM_NOT_ALLOWED(2,"不可领取"),
            ;
    private final Integer status;
    private final String desc;

    public static ClaimStatusEnum getClaimStatus(Integer claimStatus) {
        for (ClaimStatusEnum value : ClaimStatusEnum.values()) {
            if (value.getStatus().equals(claimStatus)) {
                return value;
            }
        }
        return null;
    }
}
