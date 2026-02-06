package com.mcf.relationship.controller.statistics.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author ZhuPo
 * @date 2026/2/6 10:58
 */
@Data
public class AnswerCheckinVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8645517942611524784L;
    /**
     * 统计日期
     */
    private LocalDate checkinDate;
}
