package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author ZhuPo
 * @date 2026/2/4 21:40
 */
@AllArgsConstructor
@Getter
public enum UserTypeEnum {
    USER(1,"普通用户"),
    MEMBER(2,"普通会员"),
    ;
    private final Integer type;
    private final String desc;
}
