package com.mcf.relationship.controller.exampaper.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mcf.relationship.common.dto.ProtagonistInfoDTO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZhuPo
 * @date 2026/2/3 10:50
 */
@Data
public class SimpleExamPaperVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2375989169307399914L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 试卷名称
     */
    private String name;

    /**
     * 主角信息
     */
    private ProtagonistInfoDTO protagonistInfoDTO;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;
}
