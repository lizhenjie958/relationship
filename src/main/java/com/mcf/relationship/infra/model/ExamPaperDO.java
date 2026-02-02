package com.mcf.relationship.infra.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 试卷
 * </p>
 *
 * @author baomidou
 * @since 2026-02-02
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("r_exam_paper")
public class ExamPaperDO {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 试卷名称
     */
    private String name;

    /**
     * 关系ID
     */
    private Long relationshipId;

    /**
     * 主角信息
     */
    private String protagonistInfo;

    /**
     * 出题人ID
     */
    private Long examinerId;

    /**
     * 出题人姓名
     */
    private String examinerName;

    /**
     * 试题列表
     */
    private String questionList;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
