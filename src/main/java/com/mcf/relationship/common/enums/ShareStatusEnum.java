package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ZhuPo
 * @date 2026/2/3 17:55
 */
@Getter
@AllArgsConstructor
public enum ShareStatusEnum {
    PROGRESSING(1,"进行中"),
    COMPLETE(2,"已完成"),
    EXPIRED(3,"已过期"),
    ;
    private final Integer status;
    private final String desc;
}
