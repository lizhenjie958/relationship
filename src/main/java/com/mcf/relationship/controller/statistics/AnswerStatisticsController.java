package com.mcf.relationship.controller.statistics;

import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.controller.statistics.request.AnswerCalendarQueryRequest;
import com.mcf.relationship.controller.statistics.request.AnswerStatisticsQueryRequest;
import com.mcf.relationship.controller.statistics.response.AnswerStatisticsDetailResponse;
import com.mcf.relationship.domain.service.AnswerStatisticsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ZhuPo
 * @date 2026/2/5 10:05
 */
@RestController
@RequestMapping("/answerStatistics")
public class AnswerStatisticsController {

    @Resource
    private AnswerStatisticsService answerStatisticsService;

    @PostMapping("/dataByDay")
    public McfResult<AnswerStatisticsDetailResponse> dataByDay(@RequestBody AnswerStatisticsQueryRequest request){
        AnswerStatisticsDetailResponse response = answerStatisticsService.dataByDay(request);
        return McfResult.success(response);
    }

    @PostMapping("/answerCalendar")
    public McfResult<AnswerStatisticsDetailResponse> answerCalendar(@RequestBody AnswerCalendarQueryRequest request){
        AnswerStatisticsDetailResponse response = answerStatisticsService.answerCalendar(request);
        return McfResult.success(response);
    }
}
