package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.domain.convert.AnswerStatisticsConverter;
import com.mcf.relationship.domain.entity.AnswerStatisticsBO;
import com.mcf.relationship.infra.mapper.AnswerPaperMapper;
import com.mcf.relationship.infra.mapper.AnswerStatisticsMapper;
import com.mcf.relationship.infra.model.AnswerStatisticsDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZhuPo
 * @date 2026/2/5 16:20
 */
@Component
public class AnswerStatisticsManager {
    @Resource
    private AnswerStatisticsMapper answerStatisticsMapper;

    @Resource
    private AnswerPaperMapper answerPaperMapper;

    public AnswerStatisticsBO queryOne(Long userId, LocalDate statisticsDate){
        AnswerStatisticsDO statisticsDO = this.getOne(userId, statisticsDate);
        return AnswerStatisticsConverter.do2bo(statisticsDO);
    }


    private AnswerStatisticsDO getOne(Long userId, LocalDate statisticsDate){
        AssertUtil.checkObjectNotNull(userId, "用户ID");
        AssertUtil.checkObjectNotNull(statisticsDate, "统计时间");
        LambdaQueryWrapper<AnswerStatisticsDO> queryWrapper = buildUniqueQuery(userId, statisticsDate);
        return answerStatisticsMapper.selectOne(queryWrapper, false);
    }

    /**
     * 构建唯一查询条件
     *
     * @param userId 用户ID
     * @param statisticsDate 统计时间
     * @return 查询条件
     */
    private LambdaQueryWrapper<AnswerStatisticsDO> buildUniqueQuery(Long userId, LocalDate statisticsDate) {
        LambdaQueryWrapper<AnswerStatisticsDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AnswerStatisticsDO::getUserId, userId);
        queryWrapper.eq(AnswerStatisticsDO::getStatisticsDate, statisticsDate);
        return queryWrapper;
    }

    /**
     * 答题统计
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 答题统计结果
     */
    public void answerStatistics(final LocalDateTime startTime, final LocalDateTime endTime){
        AssertUtil.checkObjectNotNull(startTime, "开始时间");
        AssertUtil.checkObjectNotNull(endTime, "结束时间");
        // 查询答题人和出题人的统计数据
        List<AnswerStatisticsDO> answerList = answerPaperMapper.statisticsByAnswer(startTime, endTime);
        List<AnswerStatisticsDO> examinerList = answerPaperMapper.statisticsByExaminer(startTime, endTime);

        ArrayList<AnswerStatisticsDO> allList = new ArrayList<>(answerList);
        allList.addAll(examinerList);

        allList.stream()
                .collect(Collectors.groupingBy(
                        AnswerStatisticsDO::getUserId,
                        Collectors.reducing(
                                (s1, s2) -> mergeStatistics(s1, s2, startTime)
                        )
                )).forEach((userId, statistics) -> {
                    AnswerStatisticsDO statisticsDO = statistics.orElseGet(null);
                    if(statisticsDO != null){
                        AnswerStatisticsDO answerStatistics4DB = getOne(userId, startTime.toLocalDate());
                        if(answerStatistics4DB == null){
                            answerStatisticsMapper.insert(statisticsDO);
                        }else{
                            statisticsDO.setId(answerStatistics4DB.getId());
                            answerStatisticsMapper.updateById(statisticsDO);
                        }
                    }
                });
    }


    /**
     * 合并两个 AnswerStatisticsDO 对象的数据
     * @param statisticsOne
     * @param statisticsTwo
     * @param statisticsDate
     * @return
     */
    private AnswerStatisticsDO mergeStatistics(AnswerStatisticsDO statisticsOne, AnswerStatisticsDO statisticsTwo, LocalDateTime statisticsDate) {
        AnswerStatisticsDO mergedData = new AnswerStatisticsDO();
        mergedData.setUserId(statisticsOne.getUserId());
        mergedData.setStatisticsDate(statisticsDate.toLocalDate());
        mergeStatistics(mergedData,statisticsOne);
        mergeStatistics(mergedData,statisticsTwo);
        return mergedData;
    }


    private void mergeStatistics(AnswerStatisticsDO mergedData, AnswerStatisticsDO data) {
        // 示例：合并答题次数和出题次数
        if(data.getAnswerCnt() != null){
            mergedData.setAnswerCnt(data.getAnswerCnt());
        }
        if(data.getAnswerMaxScoreRank() != null){
            mergedData.setAnswerMaxScoreRank(data.getAnswerMaxScoreRank());
        }
        if(data.getAnswerMaxScore() != null){
            mergedData.setAnswerMaxScore(data.getAnswerMaxScore());
        }
        if(data.getMaxScoreAnswerPaperId() != null){
            mergedData.setMaxScoreAnswerPaperId(data.getMaxScoreAnswerPaperId());
        }
        if(data.getExamCnt() != null){
            mergedData.setExamCnt(data.getExamCnt());
        }
        if(data.getExamCntRank() != null){
            mergedData.setExamCntRank(data.getExamCntRank());
        }
        if(data.getHotExamPaperId() != null){
            mergedData.setHotExamPaperId(data.getHotExamPaperId());
        }
    }
}
