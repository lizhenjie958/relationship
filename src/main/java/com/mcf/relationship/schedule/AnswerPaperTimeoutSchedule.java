package com.mcf.relationship.schedule;

import com.mcf.relationship.common.annotations.DistributedTask;
import com.mcf.relationship.infra.manager.AnswerPaperManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 答题超时状态同步
 *
 * @Author ZhuPo
 * @date 2026/2/6 20:36
 */
@Component
public class AnswerPaperTimeoutSchedule {

    @Resource
    private AnswerPaperManager answerPaperManager;

    @Scheduled(cron = "0 */10 * * * ?")
    @DistributedTask(name = "answerStatisticsSchedule#processHistory")
    public void processTimeout(){
        answerPaperManager.changeToTimeout();
    }

}
