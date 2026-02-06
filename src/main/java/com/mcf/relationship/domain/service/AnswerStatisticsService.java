package com.mcf.relationship.domain.service;

import com.mcf.relationship.controller.statistics.request.AnswerCalendarQueryRequest;
import com.mcf.relationship.controller.statistics.request.AnswerStatisticsQueryRequest;
import com.mcf.relationship.controller.statistics.response.AnswerCalendarResponse;
import com.mcf.relationship.controller.statistics.response.AnswerStatisticsDetailResponse;
import com.mcf.relationship.infra.model.AnswerStatisticsDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户答题统计表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-05
 */
public interface AnswerStatisticsService extends IService<AnswerStatisticsDO> {

    /**
     * 按天查询用户答题统计数据
     *
     * @param request 查询参数
     * @return 用户答题统计数据
     */
    AnswerStatisticsDetailResponse dataByDay(AnswerStatisticsQueryRequest request);

    /**
     * 按天查询用户答题日历数据
     *
     * @param request 查询参数
     * @return 用户答题日历数据
     */
    AnswerCalendarResponse answerCalendar(AnswerCalendarQueryRequest request);
}
