package com.mcf.relationship.domain.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author ZhuPo
 * @date 2026/2/2 12:54
 */
@Data
public class QuestionBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -487199550917722903L;
    /**
     * 问题编号
     */
    private Integer questionNo;
    /**
     * 问题相关的图片,问题仅一张图片
     */
    private String picUrl;
    /**
     * 问题模板编号
     */
    private String templateKey;
    /**
     * 问题选项
     */
    private List<OptionBO> optionList;
    /**
     * 正确选项
     */
    private List<String> correctOptionList;
}
