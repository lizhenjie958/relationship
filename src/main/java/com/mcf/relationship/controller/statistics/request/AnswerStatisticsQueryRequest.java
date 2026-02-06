package com.mcf.relationship.controller.statistics.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mcf.relationship.common.protocol.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDate;

/**
 * @author ZhuPo
 * @date 2026/2/5 10:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerStatisticsQueryRequest extends PageRequest {
    @Serial
    private static final long serialVersionUID = -8502875786168753879L;
    /**
     * 统计时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate statisticsDate;
    /**
     * 答卷完成次数
     */
    private Integer minAnswerCnt;
    /**
     * 查询完成的时间
     * 开始时间
     */
    private LocalDate startDate;
    /**
     * 查询完成的时间
     * 结束时间
     */
    private LocalDate endDate;
}
