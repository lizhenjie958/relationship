package com.mcf.relationship.controller.activity.request;

import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/7 22:44
 */
@Data
public class ParticipateRecordQueryRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = 6951110552783875493L;
    /**
     * 活动id
     */
    private Long activityId;
}
