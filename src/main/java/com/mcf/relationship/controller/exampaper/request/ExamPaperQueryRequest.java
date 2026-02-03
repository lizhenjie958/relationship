package com.mcf.relationship.controller.exampaper.request;

import com.mcf.relationship.common.protocol.PageRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @author ZhuPo
 * @date 2026/2/3 10:48
 */
@Data
public class ExamPaperQueryRequest extends PageRequest {
    @Serial
    private static final long serialVersionUID = 1533886267681900030L;
    /**
     * 出题人ID
     */
    private Long examinerId;
}
