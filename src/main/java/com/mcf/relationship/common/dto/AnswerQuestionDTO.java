package com.mcf.relationship.common.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author ZhuPo
 * @date 2026/2/4 15:35
 */
@Data
public class AnswerQuestionDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3541434880694124358L;
    /**
     * 问题编号
     */
    private Integer questionNo;

    /**
     * 用户选择的选项
     */
    private List<String> answerOptionList;
}
