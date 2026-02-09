package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * @Author ZhuPo
 * @date 2026/2/7 21:32
 */
@AllArgsConstructor
@Getter
public enum TimeUnitEnum {
    DAY(1, ChronoUnit.DAYS, "天"),
    MONTH(2, ChronoUnit.MONTHS, "月"),
    YEAR(3, ChronoUnit.YEARS, "年"),
    ;
    private final Integer unit;
    private final ChronoUnit chronoUnit;
    private final String desc;

    public static String getDesc(Integer unit) {
        for (TimeUnitEnum value : values()) {
            if (value.getUnit().equals(unit)) {
                return value.getDesc();
            }
        }
        return null;
    }

    /**
     * 默认为天
     * @param unit
     * @return
     */
    public static ChronoUnit getChronoUnitByUnit(Integer unit) {
        for (TimeUnitEnum value : values()) {
            if (value.getUnit().equals(unit)) {
                return value.getChronoUnit();
            }
        }
        return TimeUnit.DAYS.toChronoUnit();
    }
}
