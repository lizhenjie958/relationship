package com.mcf.relationship.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ZhuPo
 * @date 2026/2/2 16:49
 */
@Data
@Accessors(chain = true)
public class ProtagonistInfoBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1752455556281107220L;
    private String protagonist;
    private String picUrl;
}
