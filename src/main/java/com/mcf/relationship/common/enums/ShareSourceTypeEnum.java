package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author ZhuPo
 * @date 2025/7/13 17:01
 */
@AllArgsConstructor
@Getter
public enum ShareSourceTypeEnum {
    EXAM_PAPER(1,"试卷"),
    ;
    private final Integer type;
    private final String desc;
}
