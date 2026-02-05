package com.mcf.relationship.controller.statistics.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

/**
 * @Author ZhuPo
 * @date 2026/2/5 23:46
 */
@Data
public class AnswerCalendarResponse {
    /**
     * 每日是否答题
     */
    private Map<LocalDate,Integer> answerCountMap;
}
