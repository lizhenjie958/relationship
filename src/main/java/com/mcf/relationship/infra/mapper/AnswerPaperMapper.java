package com.mcf.relationship.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mcf.relationship.infra.model.AnswerPaperDO;
import com.mcf.relationship.infra.model.AnswerStatisticsDO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户答卷表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2026-02-04
 */
public interface AnswerPaperMapper extends BaseMapper<AnswerPaperDO> {
    List<AnswerStatisticsDO> statisticsByAnswer(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    List<AnswerStatisticsDO> statisticsByExaminer(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
