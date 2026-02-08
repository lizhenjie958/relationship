package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author ZhuPo
 * @date 2026/2/8 18:24
 */
@AllArgsConstructor
@Getter
public enum EnableStatusEnum {
    ENABLE(1,"生效"),
    DISABLE(2,"失效"),
    ;
    private final Integer status;
    private final String desc;
}
