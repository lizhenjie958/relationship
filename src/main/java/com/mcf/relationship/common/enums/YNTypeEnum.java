package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author ZhuPo
 * @date 2025/7/13 17:01
 */
@AllArgsConstructor
@Getter
public enum YNTypeEnum {
    NO(0,"否"),
    YES(1,"是"),
    ;
    private final Integer code;
    private final String desc;
}
