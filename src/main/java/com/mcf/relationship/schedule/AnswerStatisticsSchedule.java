package com.mcf.relationship.schedule;

import com.mcf.relationship.common.annotations.DistributedTask;
import com.mcf.relationship.infra.manager.AnswerStatisticsManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ZhuPo
 * @date 2026/2/5 10:27
 */
@Component
public class AnswerStatisticsSchedule {

    @Resource
    private AnswerStatisticsManager answerStatisticsManager;

    @Scheduled(cron = "1 * * * * ?")
    @DistributedTask(name = "answerStatisticsSchedule")
    public void process() {

    }
}
