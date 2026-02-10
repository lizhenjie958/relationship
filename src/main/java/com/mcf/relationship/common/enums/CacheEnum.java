package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ZhuPo
 * @date 2026/2/10 16:32
 */
@AllArgsConstructor
@Getter
public enum CacheEnum {
    CURRENT_USER(1, 1, "user:current:", "当前用户")
    ;
    private Integer type;
    // 1、写入过期； 2、获取过期
    private Integer way;
    private String prefix;
    private String desc;
}
