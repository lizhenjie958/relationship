package com.mcf.relationship.controller.exampaper.response;

import com.mcf.relationship.common.dto.ProtagonistInfoDTO;
import com.mcf.relationship.common.dto.QuestionDTO;
import com.mcf.relationship.common.enums.ClaimStatusEnum;
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
    /**
     * 试卷名称
     */
    private String name;
    /**
     * 主角信息
     */
    private ProtagonistInfoDTO protagonistInfoDTO;
    /**
     * 创建者ID
     */
    private Long examinerId;
    /**
     * 出题人姓名
     */
    private String examinerName;
    /**
     * 问题列表
     */
    private List<QuestionDTO> questionDTOList;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 开启领取状态
     * @see ClaimStatusEnum
     */
    private Integer claimStatus;
}
