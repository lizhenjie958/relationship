package com.mcf.relationship.controller.member.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author ZhuPo
 * @date 2026/2/8 09:45
 */
@Data
public class MemberAccessRecordVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8123112538310476903L;

    /**
     * 渠道码
     * @see com.mcf.relationship.common.enums.MemberAccessChannelEnum
     */
    private String accessChannelCode;

    /**
     * 获取的凭证
     *
     * 兑换方式：兑换码
     * 活动方式：活动参与ID
     */
    private String accessReceipt;

    /**
     * 单位类型
     * @see com.mcf.relationship.common.enums.TimeUnitEnum
     */
    private Integer accessUnitType;

    /**
     * 值
     * 奖励获取值
     */
    private Integer accessValue;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
