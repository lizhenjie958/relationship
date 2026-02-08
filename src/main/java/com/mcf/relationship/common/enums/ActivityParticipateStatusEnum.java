package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author ZhuPo
 * @date 2026/2/8 09:28
 */
@AllArgsConstructor
@Getter
public enum ActivityParticipateStatusEnum {
    ONGOING(1,"进行中"),
    COMPLETED(2,"已完成")
    ;
    private final Integer status;
    private final String desc;
}
