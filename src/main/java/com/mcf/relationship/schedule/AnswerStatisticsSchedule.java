package com.mcf.relationship.schedule;

import com.mcf.relationship.common.annotations.DistributedTask;
import com.mcf.relationship.infra.manager.AnswerStatisticsManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 答题统计定时任务
 *
 * @author ZhuPo
 * @date 2026/2/5 10:27
 */
@Component
public class AnswerStatisticsSchedule {

    @Resource
    private AnswerStatisticsManager answerStatisticsManager;

    @Scheduled(cron = "0 */10 * * * ?")
    @DistributedTask(name = "answerStatisticsSchedule")
    public void process() {
        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        answerStatisticsManager.answerStatistics(startTime, startTime.plusDays(1).minusSeconds(1));
    }
}
