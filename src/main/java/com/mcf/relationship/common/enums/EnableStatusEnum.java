package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ZhuPo
 * @date 2026/2/3 16:13
 */
@AllArgsConstructor
@Getter
public enum EnableStatusEnum {
    ENABLE(1,"启用"),
    DISABLE(2,"停用"),
            ;
    private final Integer status;
    private final String desc;
}
