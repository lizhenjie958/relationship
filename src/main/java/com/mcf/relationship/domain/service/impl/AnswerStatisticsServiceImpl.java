package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.statistics.request.AnswerCalendarQueryRequest;
import com.mcf.relationship.controller.statistics.request.AnswerStatisticsQueryRequest;
import com.mcf.relationship.controller.statistics.response.AnswerStatisticsDetailResponse;
import com.mcf.relationship.domain.convert.AnswerStatisticsConverter;
import com.mcf.relationship.domain.entity.AnswerStatisticsBO;
import com.mcf.relationship.domain.service.AnswerStatisticsService;
import com.mcf.relationship.infra.manager.AnswerStatisticsManager;
import com.mcf.relationship.infra.manager.RedisManager;
import com.mcf.relationship.infra.mapper.AnswerStatisticsMapper;
import com.mcf.relationship.infra.model.AnswerStatisticsDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    private RedisManager redisManager;

    @Override
    public AnswerStatisticsDetailResponse dataByDay(AnswerStatisticsQueryRequest request) {
        AssertUtil.checkObjectNotNull(request.getStatisticsTime(), "统计时间");
        AnswerStatisticsBO answerStatisticsBO = answerStatisticsManager.queryOne(UserLoginContextUtil.getUserId(), request.getStatisticsTime().toLocalDate());
        return AnswerStatisticsConverter.bo2detail(answerStatisticsBO);
    }

    @Override
    public AnswerStatisticsDetailResponse answerCalendar(AnswerCalendarQueryRequest request) {
        return null;
    }
}
