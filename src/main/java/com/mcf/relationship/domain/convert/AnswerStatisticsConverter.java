package com.mcf.relationship.domain.convert;

import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.domain.entity.AnswerStatisticsBO;
import com.mcf.relationship.infra.model.AnswerStatisticsDO;

/**
 * @author ZhuPo
 * @date 2026/2/5 16:34
 */
public final class AnswerStatisticsConverter {
    public static AnswerStatisticsBO convert(AnswerStatisticsDO answerStatisticsDO) {
        if (answerStatisticsDO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(answerStatisticsDO, new AnswerStatisticsBO());
    }
}
