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
    INVITER_NOT_EXIST(1101, "邀请人不存在"),
    USER_HAS_INVITER(1102, "您已维护过邀请人"),

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
    EXAM_PAPER_NOT_CLAIMABLE(1302, "该试卷不可认领"),

    /**
     * 分享相关异常
     * 1401-1500
     */
    SHARE_FAIL(1401, "分享失败,请稍后重试"),
    SHARE_NOT_EXIST(1402, "分享不存在"),
    SHARE_STOPPED(1403, "分享已停止"),
    SHARE_EXPIRED(1404, "分享已过期"),
    SHARE_STATUS_ERROR(1405, "分享状态处于:{0}"),
    /**
     * 答题相关异常
     * 1501-1600
     */
    ANSWER_PAPER_STATUS_ERROR(1501, "答卷当前处于{0}状态，{1}"),

    /**
     * 活动相关异常
     * 1601-1700
     */
    HAS_PARTICIPATE_ACTIVITY(1601, "您已经参加过该活动"),
    ACTIVITY_NOT_EXIST(1602, "您所参加的活动不存在"),
    ACTIVITY_STATUS_ERROR(1603, "参加失败，活动状态处于:{0}"),
    ACTIVITY_NOT_START(1702, "活动未开始"),
    ACTIVITY_ENDED(1703, "活动已结束"),


    /**
     * 兑换码相关异常
     * 1701-1800
     */
    REDEEM_CODE_NOT_EXIST(1701, "兑换码不存在"),
    REDEEM_CODE_STATUS_ERROR(1704, "兑换码状态处于:{0}"),
    REDEEM_FAILED(1705, "兑换失败，请稍后重试"),
    ACTIVITY_NOT_SUPPORT_REDEEM_MEMBER(1706, "该活动不支持兑换会员");

    private final Integer code;
    private final String msg;
}
