package com.mcf.relationship.controller.answerpaper.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mcf.relationship.common.protocol.PageRequest;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ZhuPo
 * @date 2026/2/4 15:04
 */
@Data
public class AnswerPaperQueryRequest extends PageRequest {
    @Serial
    private static final long serialVersionUID = -6948682047314770205L;
    /**
     * 状态
     * @see com.mcf.relationship.common.enums.AnswerStatusEnum
     */
    private Integer answerStatus;
    private List<Integer> answerStatusList;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 出题人ID
     */
    private Long examinerId;
}
