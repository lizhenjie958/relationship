package com.mcf.relationship.controller.statistics.request;

import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/5 23:45
 */
@Data
public class AnswerCalendarQueryRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = 3076484772977997639L;
    private Integer year;
    private Integer month;
}
