package com.mcf.relationship.common.enums;

import com.mcf.relationship.common.consts.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author ZhuPo
 * @date 2026/2/8 09:06
 */
@AllArgsConstructor
@Getter
public enum ActivityStatusEnum {
    NOT_STARTED(1,"未开始"),
    START(2,"已开始"),
    END(3,"已结束"),
    STOP(4,"已停止"),
    ;
    private final Integer status;
    private final String desc;

    public static String getDesc(Integer activityStatus) {
        if (activityStatus == null){
            return CommonConst.UNKNOWN;
        }
        for (ActivityStatusEnum value : values()) {
            if (value.getStatus().equals(activityStatus)) {
                return value.getDesc();
            }
        }
        return CommonConst.UNKNOWN;
    }
}
