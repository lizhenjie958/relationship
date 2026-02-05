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
import java.util.List;
import java.util.Map;
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

    /**
     * 插入数据
     *
     * @param answerStatisticsBO 实体对象
     */
    public void insert(AnswerStatisticsBO answerStatisticsBO) {
        answerStatisticsMapper.insert(answerStatisticsBO);
    }

    /**
     * 更新数据
     *
     * @param answerStatisticsBO 实体对象
     */
    public void update(AnswerStatisticsBO answerStatisticsBO) {
        answerStatisticsMapper.updateById(answerStatisticsBO);
    }


    public AnswerStatisticsBO queryOne(Long userId, LocalDate statisticsDate){
        AssertUtil.checkObjectNotNull(userId, "用户ID");
        AssertUtil.checkObjectNotNull(statisticsDate, "统计时间");
        LambdaQueryWrapper<AnswerStatisticsDO> queryWrapper = buildUniqueQuery(userId, statisticsDate);
        AnswerStatisticsDO answerStatisticsDO = answerStatisticsMapper.selectOne(queryWrapper, false);
        return AnswerStatisticsConverter.convert(answerStatisticsDO);
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
    public List<AnswerStatisticsBO> answerStatistics(LocalDateTime startTime, LocalDateTime endTime){
        AssertUtil.checkObjectNotNull(startTime, "开始时间");
        AssertUtil.checkObjectNotNull(endTime, "结束时间");
        // 查询答题人和出题人的统计数据
        List<AnswerStatisticsDO> answerList = answerPaperMapper.statisticsByAnswer(startTime, endTime);
        List<AnswerStatisticsDO> examinerList = answerPaperMapper.statisticsByExaminer(startTime, endTime);

        // 转换为 Map，以 userId 为 key
        Map<Long, AnswerStatisticsDO> answerMap = answerList.stream()
                .collect(Collectors.toMap(AnswerStatisticsDO::getUserId, statistics -> statistics));
        Map<Long, AnswerStatisticsDO> examinerMap = examinerList.stream()
                .collect(Collectors.toMap(AnswerStatisticsDO::getUserId, statistics -> statistics));

        // 合并两个 Map 的数据
        List<AnswerStatisticsBO> result = answerMap.entrySet().stream()
                .map(entry -> {
                    Long userId = entry.getKey();
                    AnswerStatisticsDO answerData = entry.getValue();
                    AnswerStatisticsDO examinerData = examinerMap.get(userId);

                    // 如果 examinerMap 中存在对应数据，则合并；否则直接使用 answerData
                    if (examinerData != null) {
                        AnswerStatisticsDO mergedData = mergeStatistics(answerData, examinerData,startTime);
                        return AnswerStatisticsConverter.convert(mergedData);
                    } else {
                        return AnswerStatisticsConverter.convert(answerData);
                    }
                })
                .collect(Collectors.toList());

        // 处理 examinerMap 中独有的数据
        examinerMap.entrySet().stream()
                .filter(entry -> !answerMap.containsKey(entry.getKey())) // 过滤掉已处理的 userId
                .forEach(entry -> result.add(AnswerStatisticsConverter.convert(entry.getValue())));

        return result;

    }

    /**
     * 合并两个 AnswerStatisticsDO 对象的数据
     *
     * @param answerData   答题人数据
     * @param examinerData 出题人数据
     * @return 合并后的数据
     */
    private AnswerStatisticsDO mergeStatistics(AnswerStatisticsDO answerData, AnswerStatisticsDO examinerData, LocalDateTime statisticsDate) {
        AnswerStatisticsDO mergedData = new AnswerStatisticsDO();
        mergedData.setUserId(answerData.getUserId());
        mergedData.setStatisticsDate(statisticsDate.toLocalDate());

        // 示例：合并答题次数和出题次数
        mergedData.setAnswerCnt(answerData.getAnswerCnt());
        mergedData.setAnswerMaxScoreRank(answerData.getAnswerMaxScoreRank());
        mergedData.setAnswerMaxScore(answerData.getAnswerMaxScore());
        mergedData.setMaxScoreAnswerPaperId(answerData.getMaxScoreAnswerPaperId());

        mergedData.setExamCnt(examinerData.getExamCnt());
        mergedData.setExamCntRank(examinerData.getExamCntRank());
        mergedData.setHotExamPaperId(examinerData.getHotExamPaperId());

        return mergedData;
    }
}
