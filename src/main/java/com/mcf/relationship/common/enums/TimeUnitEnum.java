package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @Author ZhuPo
 * @date 2026/2/7 21:32
 */
@AllArgsConstructor
@Getter
public enum TimeUnitEnum {
    DAY(1, TimeUnit.DAYS, "天"),
    MONTH(2,TimeUnit.MINUTES, "月"),
    YEAR(3,TimeUnit.DAYS, "年"),
    ;
    private final Integer unit;
    private final TimeUnit timeUnit;
    private final String desc;

    public static String getDesc(Integer unit) {
        for (TimeUnitEnum value : values()) {
            if (value.getUnit().equals(unit)) {
                return value.getDesc();
            }
        }
        return null;
    }

    public static TimeUnit getTimeUnit(Integer unit) {
        for (TimeUnitEnum value : values()) {
            if (value.getUnit().equals(unit)) {
                return value.getTimeUnit();
            }
        }
        return null;
    }
}
