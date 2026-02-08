package com.mcf.relationship.controller.activity.response;

import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDate;

/**
 * @Author ZhuPo
 * @date 2026/2/8 08:50
 */
@Data
public class CurrentActivityResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = 2342250635117747367L;

    /**
     * 活动ID
     */
    private Long id;

    /**
     * 条件阈值
     */
    private Integer threshold;

    /**
     * 奖励值
     */
    private Integer reward;

    /**
     * 奖励单位
     */
    private Integer rewardUnitType;

    /**
     * 开始时间
     */
    private LocalDate startDate;

    /**
     * 结束时间
     */
    private LocalDate endDate;
}
