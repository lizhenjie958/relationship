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
    ;
    private final Integer code;
    private final String msg;
}
