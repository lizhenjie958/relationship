package com.mcf.relationship.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhuPo
 * @date 2026/2/6 14:47
 */
@Data
@Accessors(chain = true)
public class AnswerStatisticsHistoryBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8982091554923819998L;
    /**
     * 签到时间表
     * 只记录签到的时间，不记录未签到的时间
     */
    private List<LocalDate> checkinDates = new ArrayList<>();
}
