package com.mcf.relationship.common.enums;

import com.mcf.relationship.common.consts.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author ZhuPo
 * @date 2026/2/8 11:07
 */
@AllArgsConstructor
@Getter
public enum RedeemStatusEnum {
    WAIT_USE(1,"待使用"),
    USED(2,"已使用"),
    EXPIRED(3,"已过期"),
    ;
    private final Integer status;
    private final String desc;

    public static Object getDesc(Integer redeemStatus) {
        if (redeemStatus == null){
            return CommonConst.UNKNOWN;
        }
        for (RedeemStatusEnum redeemStatusEnum : RedeemStatusEnum.values()) {
            if (redeemStatusEnum.getStatus().equals(redeemStatus)){
                return redeemStatusEnum.getDesc();
            }
        }
        return CommonConst.UNKNOWN;
    }
}
