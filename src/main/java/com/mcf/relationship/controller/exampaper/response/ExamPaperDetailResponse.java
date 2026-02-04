package com.mcf.relationship.controller.exampaper.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mcf.relationship.common.dto.ProtagonistInfoDTO;
import com.mcf.relationship.common.dto.QuestionDTO;
import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.Data;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author ZhuPo
 * @date 2026/2/2 22:50
 */
@Data
public class ExamPaperDetailResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = -7851457385726876322L;
    private String name;
    private ProtagonistInfoDTO protagonistInfoDTO;
    private List<QuestionDTO> questionDTOList;
    private LocalDateTime createTime;
}
