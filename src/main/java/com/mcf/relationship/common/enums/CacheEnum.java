package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author ZhuPo
 * @date 2026/2/10 16:32
 */
@AllArgsConstructor
@Getter
public enum CacheEnum {
    CURRENT_USER(1 , "user:current:", "当前用户"),
    ANSWER_CHECK_IN_HISTORY(2, "answer:checkin:history:","答题签到历史"),
    ANSWER_CHECK_IN_NOW(3, "answer:checkin:now:","当前答题签到"),
    ;
    private final Integer type;
    private final String prefix;
    private final String desc;

    public String buildKey(Object... key) {
        return this.prefix + Arrays.stream(key)
                .map(String::valueOf)
                .collect(Collectors.joining(":"));
    }
}
