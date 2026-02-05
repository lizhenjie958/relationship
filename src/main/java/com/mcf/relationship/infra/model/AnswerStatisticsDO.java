package com.mcf.relationship.infra.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户答题统计表
 * </p>
 *
 * @author baomidou
 * @since 2026-02-05
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("r_answer_statistics")
public class AnswerStatisticsDO implements Serializable {


    @Serial
    private static final long serialVersionUID = -7672449740764113773L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 统计日期
     */
    private LocalDate statisticsDate;

    /**
     * 答卷完成次数
     */
    private Long answerCnt;

    /**
     * 最高得分的答卷ID
     */
    private Long maxScoreAnswerPaperId;

    /**
     * 答卷最高得分
     */
    private Integer answerMaxScore;

    /**
     * 答卷最高得分排名
     */
    private Long answerMaxScoreRank;

    /**
     * 考卷总答题人次
     */
    private Long examCnt;

    /**
     * 考卷总答题人次排名
     */
    private Long examCntRank;

    /**
     * 热门试卷ID
     */
    private Long hotExamPaperId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
