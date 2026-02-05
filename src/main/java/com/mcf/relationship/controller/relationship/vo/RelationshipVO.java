package com.mcf.relationship.controller.relationship.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ZhuPo
 * @date 2026/1/24 19:11
 */
@Data
public class RelationshipVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3991640086906832721L;
    private Long id;
    private String protagonist;
    private String picUrl;
    private String remark;
}
