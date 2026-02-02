package com.mcf.relationship.domain.entity;

import com.mcf.relationship.common.dto.ProtagonistInfoDTO;
import com.mcf.relationship.common.dto.QuestionDTO;
import com.mcf.relationship.infra.model.ExamPaperDO;
import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * @Author ZhuPo
 * @date 2026/2/2 22:27
 */
@Data
public class ExamPaperBO extends ExamPaperDO {
    @Serial
    private static final long serialVersionUID = 3151493095580306097L;
    private List<QuestionDTO> questionDTOList;
    private ProtagonistInfoDTO protagonistInfoDTO;
}
