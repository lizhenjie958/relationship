package com.mcf.relationship.controller.answerpaper.response;

import com.mcf.relationship.common.dto.ProtagonistInfoDTO;
import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author ZhuPo
 * @date 2026/2/5 9:22
 */
@Data
public class SimpleAnswerPaperResponse extends BaseResponse {

    @Serial
    private static final long serialVersionUID = -8995829619795478066L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 试卷ID
     */
    private Long examPaperId;

    /**
     * 答题状态
     * @see  com.mcf.relationship.common.enums.AnswerStatusEnum
     */
    private Integer answerStatus;

    /**
     * 答题分数
     */
    private Integer score;

    /**
     * 试卷名称
     */
    private String examPaperName;
    /**
     * 主角信息
     */
    private ProtagonistInfoDTO protagonistInfoDTO;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 超时时间
     */
    private LocalDateTime timeoutTime;
}
