package com.mcf.relationship.controller.statistics.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author ZhuPo
 * @date 2026/2/5 10:09
 */
@Data
public class AnswerStatisticsQueryRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = -8502875786168753879L;
    /**
     * 统计时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime statisticsTime;
}
