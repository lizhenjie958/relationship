package com.mcf.relationship.controller.exampaper.response;

import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.Data;

import java.io.Serial;

/**
 * @author ZhuPo
 * @date 2026/2/2 11:09
 */
@Data
public class GenerateExamPaperResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = 6061673011241628754L;
    /**
     * 试卷ID
     */
    private Long id;
}
