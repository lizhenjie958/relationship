package com.mcf.relationship.common.enums;

import com.mcf.relationship.common.consts.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ZhuPo
 * @date 2026/2/3 16:13
 */
@AllArgsConstructor
@Getter
public enum ShareStatusEnum {
    SHARING(1,"分享中"),
    STOPPED(2,"已停止"),
    EXPIRED(3,"已过期"),
            ;
    private final Integer status;
    private final String desc;

    public static String getDesc(Integer shareStatus) {
        for (ShareStatusEnum value : values()) {
            if (value.status.equals(shareStatus)) {
                return value.desc;
            }
        }
        return CommonConst.UNKNOWN;
    }
}
