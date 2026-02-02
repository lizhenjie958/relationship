package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author ZhuPo
 * @date 2026/1/24 19:46
 */
@AllArgsConstructor
@Getter
public enum BizExceptionEnum implements ExceptionInterface {
    /**
     * 授权相关异常
     * 1001-1100
     */
    NEED_LOGIN(1001,"用户未登录"),
    LOGIN_EXPIRED(1002, "登录已过期"),

    /**
     * 用户相关异常
     * 1101-1200
     */

    /**
     * 关系相关异常
     * 1201-1300
     */
    PROTAGONIST_EXIST(1201, "该主角已存在"),

    /**
     * 试卷相关异常
     * 1301-1400
     */
    RELATION_NOT_ENOUGH_CHARACTERS(1301, "关系种类数量不足{0}个");
    ;
    private final Integer code;
    private final String msg;
}
