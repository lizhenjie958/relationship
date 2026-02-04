package com.mcf.relationship.controller.answerpaper.request;

import com.mcf.relationship.common.protocol.PageRequest;
import lombok.Data;

import java.io.Serial;

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
}
