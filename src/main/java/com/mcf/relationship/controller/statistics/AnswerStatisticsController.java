package com.mcf.relationship.controller.statistics;

import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.controller.statistics.request.AnswerStatisticsQueryRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhuPo
 * @date 2026/2/5 10:05
 */
@RestController
@RequestMapping("/answerStatistics")
public class AnswerStatisticsController {

    @PostMapping("/dataByDay")
    public McfResult dataByDay(@RequestBody AnswerStatisticsQueryRequest request){
        return null;
    }
}
