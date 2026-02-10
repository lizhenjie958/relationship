package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author ZhuPo
 * @date 2026/2/8 18:24
 */
@AllArgsConstructor
@Getter
public enum RelationshipTypeEnum {
    USER(1,"个人"),
    COMMON(2,"通用"),
    ;
    private final Integer type;
    private final String desc;
}
