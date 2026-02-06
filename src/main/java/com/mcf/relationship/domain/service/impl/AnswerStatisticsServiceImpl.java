package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.statistics.request.AnswerCalendarQueryRequest;
import com.mcf.relationship.controller.statistics.request.AnswerStatisticsQueryRequest;
import com.mcf.relationship.controller.statistics.response.AnswerCalendarResponse;
import com.mcf.relationship.controller.statistics.response.AnswerStatisticsDetailResponse;
import com.mcf.relationship.domain.convert.AnswerStatisticsConverter;
import com.mcf.relationship.domain.entity.AnswerStatisticsBO;
import com.mcf.relationship.domain.entity.AnswerStatisticsHistoryBO;
import com.mcf.relationship.domain.service.AnswerStatisticsService;
import com.mcf.relationship.infra.manager.AnswerStatisticsCacheManager;
import com.mcf.relationship.infra.manager.AnswerStatisticsManager;
import com.mcf.relationship.infra.mapper.AnswerStatisticsMapper;
import com.mcf.relationship.infra.model.AnswerStatisticsDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * <p>
 * 用户答题统计表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-05
 */
@Service
public class AnswerStatisticsServiceImpl extends ServiceImpl<AnswerStatisticsMapper, AnswerStatisticsDO> implements AnswerStatisticsService {

    @Resource
    private AnswerStatisticsManager answerStatisticsManager;

    @Resource
    private AnswerStatisticsCacheManager answerStatisticsCacheManager;

    @Override
    public AnswerStatisticsDetailResponse dataByDay(AnswerStatisticsQueryRequest request) {
        AssertUtil.checkObjectNotNull(request.getStatisticsDate(), "统计时间");
        AnswerStatisticsBO answerStatisticsBO = answerStatisticsManager.queryOne(UserLoginContextUtil.getUserId(), request.getStatisticsDate());
        return AnswerStatisticsConverter.bo2detail(answerStatisticsBO);
    }

    @Override
    public AnswerCalendarResponse answerCalendar(AnswerCalendarQueryRequest request) {
        AssertUtil.checkObjectNotNull(request.getCheckinMonth(), "签到时间");
        AnswerStatisticsHistoryBO historyCheckin = answerStatisticsCacheManager.getHistoryCheckin(UserLoginContextUtil.getUserId(), request.getCheckinMonth());
        LocalDate now = LocalDate.now();
        LocalDate thisMonth = LocalDate.of(now.getYear(), now.getMonth(), 1);
        // 如果获取的签到时间小于当前月份，则获取直接返回即可
        if(!request.getCheckinMonth().isBefore(thisMonth)) {
            // 需获取今日数据
            Integer nowCheckIn = answerStatisticsCacheManager.getNowCheckIn(UserLoginContextUtil.getUserId(), now);
            if(nowCheckIn != null && nowCheckIn == 1){
                historyCheckin.getCheckinDates().add(now);
            }
        }
        AnswerCalendarResponse answerCalendarResponse = new AnswerCalendarResponse();
        answerCalendarResponse.setCheckinDateList(historyCheckin.getCheckinDates());
        return answerCalendarResponse;
    }
}
