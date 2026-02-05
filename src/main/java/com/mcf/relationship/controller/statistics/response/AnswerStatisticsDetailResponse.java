package com.mcf.relationship.controller.statistics.response;

import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/5 22:47
 */
@Data
public class AnswerStatisticsDetailResponse extends BaseResponse {

    @Serial
    private static final long serialVersionUID = 8441531745451028400L;
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

}
