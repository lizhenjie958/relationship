package com.mcf.relationship.infra.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户答卷表
 * </p>
 *
 * @author baomidou
 * @since 2026-02-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("r_answer_paper")
public class AnswerPaperDO implements Serializable {


    @Serial
    private static final long serialVersionUID = -6784131987907021772L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 答题人
     */
    private Long answerId;

    /**
     * 主角信息
     */
    private String protagonistInfo;

    /**
     * 领取时间
     */
    private LocalDateTime claimTime;

    /**
     * 过期时间
     */
    private LocalDateTime timeoutTime;

    /**
     * 答题状态1答题中2已完成3已放弃4已过期
     */
    private Integer answerStatus;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 放弃时间
     */
    private LocalDateTime giveUpTime;

    /**
     * 答题列表
     */
    private String answers;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
