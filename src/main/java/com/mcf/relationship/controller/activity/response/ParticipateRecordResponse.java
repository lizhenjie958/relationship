package com.mcf.relationship.controller.activity.response;

import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @Author ZhuPo
 * @date 2026/2/8 08:54
 */
@Data
public class ParticipateRecordResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = 2299125182927269116L;
    /**
     * 参加时间
     */
    private LocalDateTime participateTime;

    /**
     * 参加状态
     * @see com.mcf.relationship.common.enums.ActivityParticipateStatusEnum
     */
    private Integer participateStatus;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 当前指标
     */
    private Integer currentIndicator;
}
