package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 会员获取渠道枚举
 *
 * @Author ZhuPo
 * @date 2026/2/7 20:39
 */
@AllArgsConstructor
@Getter
public enum MemberAccessChannelEnum {
    INVITE_USER_ROLLUP("c8z85k","邀请好友注册"),
    CUMULATE_ANSWER_CHECKIN("jm6nm2","累计答题签到"),
    REDEEM_MEMBER("e6hn9u","兑换码兑换"),
            ;
    private final String code;
    private final String desc;

    /**
     * 根据code获取枚举
     * @param channelCode
     * @return
     */
    public static MemberAccessChannelEnum getByCode(String channelCode) {
        for (MemberAccessChannelEnum value : MemberAccessChannelEnum.values()) {
            if (value.getCode().equals(channelCode)) {
                return value;
            }
        }
        return null;
    }
}
