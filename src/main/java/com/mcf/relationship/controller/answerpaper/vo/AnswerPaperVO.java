package com.mcf.relationship.controller.answerpaper.vo;

import com.mcf.relationship.common.dto.ProtagonistInfoDTO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZhuPo
 * @date 2026/2/4 15:06
 */
@Data
public class AnswerPaperVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6295029487959331264L;
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
    /**
     * 完成时间
     */
    private LocalDateTime completeTime;
    /**
     * 放弃时间
     */
    private LocalDateTime giveUpTime;
}
