package com.mcf.relationship.domain.convert;

import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.controller.statistics.response.AnswerStatisticsDetailResponse;
import com.mcf.relationship.domain.entity.AnswerStatisticsBO;
import com.mcf.relationship.infra.model.AnswerStatisticsDO;

/**
 * @author ZhuPo
 * @date 2026/2/5 16:34
 */
public final class AnswerStatisticsConverter {
    public static AnswerStatisticsBO do2bo(AnswerStatisticsDO answerStatisticsDO) {
        if (answerStatisticsDO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(answerStatisticsDO, new AnswerStatisticsBO());
    }

    public static AnswerStatisticsDetailResponse bo2detail(AnswerStatisticsBO statisticsBO){
        if (statisticsBO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(statisticsBO, new AnswerStatisticsDetailResponse());
    }
}
