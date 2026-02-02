package com.mcf.relationship.common.protocol;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ZhuPo
 * @date 2026/1/24 18:58
 */
@Data
public class BaseRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -7008411545266286296L;
    /**
     * 默认为当前用户ID
     */
    private Long userId;
}
