package com.mcf.relationship.controller.statistics.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDate;

/**
 * @Author ZhuPo
 * @date 2026/2/5 23:45
 */
@Data
public class AnswerCalendarQueryRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = 3076484772977997639L;
    /**
     * 签到时间
     * 每月的第一天
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkinMonth;
}
