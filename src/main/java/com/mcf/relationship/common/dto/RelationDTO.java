package com.mcf.relationship.common.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ZhuPo
 * @date 2026/2/1 16:40
 */
@Data
public class RelationDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -7188498588395181556L;
    private String relationName;
    private String picUrl;
}
