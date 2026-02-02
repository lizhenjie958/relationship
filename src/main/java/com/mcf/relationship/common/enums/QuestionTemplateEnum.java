package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ZhuPo
 * @date 2026/2/2 14:51
 */
@Getter
@AllArgsConstructor
public enum QuestionTemplateEnum {
    GUESS_RELATION("cg3j4e","图片中的人物是主角的()"),
    ;
    private final String key;
    private final String desc;
}
