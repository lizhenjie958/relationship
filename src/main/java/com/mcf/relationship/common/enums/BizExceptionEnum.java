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
    WX_AUTH_ERROR(1003, "微信授权失败"),
    USER_ROLL_UP_FAILED(1004, "用户注册失败"),
    USER_NOT_EXIST(1005, "用户不存在"),

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
    RELATION_NOT_ENOUGH_CHARACTERS(1301, "关系种类数量不足{0}个"),

    /**
     * 分享相关异常
     * 1401-1500
     */
    SHARE_FAIL(1401, "分享失败,请稍后重试"),
    SHARE_NOT_EXIST(1402, "分享不存在"),
    SHARE_DISABLED(1403, "分享已停用"),
    SHARE_EXPIRED(1404, "分享已过期"),

    /**
     * 答题相关异常
     * 1501-1600
     */
    ANSWER_PAPER_STATUS_ERROR(1501, "答卷当前处于｛0｝状态，｛1｝"),
    ;

    private final Integer code;
    private final String msg;
}
