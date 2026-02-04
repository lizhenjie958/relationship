package com.mcf.relationship.controller.answerpaper.response;

import com.mcf.relationship.common.dto.AnswerQuestionDTO;
import com.mcf.relationship.common.dto.ProtagonistInfoDTO;
import com.mcf.relationship.common.dto.QuestionDTO;
import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ZhuPo
 * @date 2026/2/4 15:31
 */
@Data
public class AnswerPaperDetailResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = 4864499186985998866L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 试卷ID
     */
    private Long examPaperId;
    /**
     * 试卷名称
     */
    private String examPaperName;
    /**
     * 主角信息
     */
    private ProtagonistInfoDTO protagonistInfoDTO;

    /**
     * 答题状态
     * @see com.mcf.relationship.common.enums.AnswerStatusEnum
     */
    private String answerStatus;

    /**
     * 分数
     */
    private Integer score;

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
    /**
     * 试卷问题
     */
    private List<QuestionDTO> questionDTOList;

    /**
     * 用户答案
     */
    private List<AnswerQuestionDTO> answerQuestionDTOList;
}

