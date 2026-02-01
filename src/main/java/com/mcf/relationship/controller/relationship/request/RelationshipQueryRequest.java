package com.mcf.relationship.controller.relationship.request;

import com.mcf.relationship.common.protocol.PageRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/1 15:41
 */
@Data
public class RelationshipQueryRequest extends PageRequest {
    @Serial
    private static final long serialVersionUID = 8848933999327540337L;
    /**
     * 主角
     */
    private String protagonist;
}
