package com.mcf.relationship.schedule;

import com.mcf.relationship.common.annotations.DistributedTask;
import com.mcf.relationship.common.consts.NumberConst;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.statistics.request.AnswerStatisticsQueryRequest;
import com.mcf.relationship.domain.entity.AnswerStatisticsBO;
import com.mcf.relationship.domain.entity.AnswerStatisticsHistoryBO;
import com.mcf.relationship.infra.manager.AnswerStatisticsCacheManager;
import com.mcf.relationship.infra.manager.AnswerStatisticsManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统计用户答题签到
 *
 * @author ZhuPo
 * @date 2026/2/5 10:27
 */
@Component
public class AnswerCheckinStatisticsSchedule {

    @Resource
    private AnswerStatisticsManager answerStatisticsManager;

    @Resource
    private AnswerStatisticsCacheManager answerStatisticsCacheManager;

    @Scheduled(cron = "1 0 1,3,5 * * ?")
//    @Scheduled(cron = "1 * * * * ?")
    @DistributedTask(name = "answerStatisticsSchedule#processHistory")
    public void processHistory() {
        LocalDate now = LocalDate.now();

        LocalDate endTime = now.minusDays(1);
        LocalDate startTime = LocalDate.of(now.getYear(), now.getMonth(), 1);

        AnswerStatisticsQueryRequest queryRequest = new AnswerStatisticsQueryRequest();
        queryRequest.setStartDate(startTime);
        queryRequest.setEndDate(endTime);
        queryRequest.setMinAnswerCnt(NumberConst.ONE);
        queryRequest.setPageSize(NumberConst.HUNDRED);
        for (int pageNo = 1; pageNo <= NumberConst.THOUSAND; pageNo++){
            queryRequest.setPageNo(pageNo);
            PageResponse<AnswerStatisticsBO> response = answerStatisticsManager.queryList(queryRequest);
            if(CollectionUtils.isEmpty(response.getList())){
                return;
            }
            Map<Long, List<LocalDate>> userIdDateMap = response.getList()
                    .stream().collect(Collectors.groupingBy(
                            AnswerStatisticsBO::getUserId, Collectors.mapping(AnswerStatisticsBO::getStatisticsDate, Collectors.toList()))
                    );
            userIdDateMap.forEach((userId, statisticsDates) -> {
                AnswerStatisticsHistoryBO historyBO = new AnswerStatisticsHistoryBO().setCheckinDates(statisticsDates);
                answerStatisticsCacheManager.saveHistoryCheckin(userId, startTime, historyBO);
            });
        }
    }


//    @Scheduled(cron = "1 * 6-23 * * ?")
    @Scheduled(cron = "10 * * * * ?")
    @DistributedTask(name = "answerStatisticsSchedule#processNow")
    public void processNow() {
        LocalDate now = LocalDate.now();
        AnswerStatisticsQueryRequest queryRequest = new AnswerStatisticsQueryRequest();
        queryRequest.setStatisticsDate(now);
        queryRequest.setMinAnswerCnt(NumberConst.ONE);
        queryRequest.setPageSize(NumberConst.HUNDRED);
        for (int pageNo = 1; pageNo <= NumberConst.THOUSAND; pageNo++){
            queryRequest.setPageNo(pageNo);
            PageResponse<AnswerStatisticsBO> response = answerStatisticsManager.queryList(queryRequest);
            if(CollectionUtils.isEmpty(response.getList())){
                return;
            }
            List<Long> userIdList = response.getList()
                    .stream().map(AnswerStatisticsBO::getUserId).toList();
            userIdList.forEach(userId -> {
                answerStatisticsCacheManager.saveNowCheckIn(userId, now, NumberConst.ONE);
            });
        }
    }
}
