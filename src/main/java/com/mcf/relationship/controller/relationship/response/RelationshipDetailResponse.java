package com.mcf.relationship.controller.relationship.response;

import com.mcf.relationship.common.protocol.BaseResponse;
import com.mcf.relationship.controller.relationship.vo.RelationVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * @Author ZhuPo
 * @date 2026/2/1 18:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RelationshipDetailResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = 6003652915439289800L;
    private String protagonist;
    private String picUrl;
    private String remark;
    private List<RelationVO> relationList;
}
