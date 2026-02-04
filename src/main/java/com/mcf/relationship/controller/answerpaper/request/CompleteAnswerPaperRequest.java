package com.mcf.relationship.controller.answerpaper.request;

import com.mcf.relationship.common.dto.AnswerQuestionDTO;
import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * @author ZhuPo
 * @date 2026/2/4 15:44
 */
@Data
public class CompleteAnswerPaperRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = -457801328750208522L;
    /**
     * 答卷ID
     */
    private Long id;
    /**
     * 用户答案
     */
    private List<AnswerQuestionDTO> answerQuestionDTOList;
}
