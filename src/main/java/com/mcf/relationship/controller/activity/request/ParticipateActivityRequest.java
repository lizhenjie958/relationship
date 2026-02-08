package com.mcf.relationship.controller.activity.request;

import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/7 22:40
 */
@Data
public class ParticipateActivityRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = 5859049213770680851L;
    /**
     * 活动id
     */
    private Long activityId;
}
