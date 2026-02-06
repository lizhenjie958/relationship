package com.mcf.relationship.controller.statistics.response;

import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDate;
import java.util.List;

/**
 * @Author ZhuPo
 * @date 2026/2/5 23:46
 */
@Data
public class AnswerCalendarResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = -4326485728327282663L;
    /**
     * 每日是否答题
     */
    private List<LocalDate> checkinDateList;
}
