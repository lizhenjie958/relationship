package com.mcf.relationship.controller.relationship.request;

import com.mcf.relationship.common.protocol.BaseRequest;
import com.mcf.relationship.common.dto.RelationDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * @Author ZhuPo
 * @date 2026/2/1 16:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RelationshipUpdateRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = -1800464343464296295L;
    private Long id;
    private String protagonist;
    private String picUrl;
    private String remark;
    private List<RelationDTO> relationList;
}
