package com.mcf.relationship.controller.exampaper.request;

import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @author ZhuPo
 * @date 2026/2/2 11:06
 */
@Data
public class GenerateExamPaperRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = -4550552043632112984L;
    /**
     * 关系id
     */
    private Long relationshipId;
    /**
     * 试卷名称
     */
    private String examPaperName;
}
