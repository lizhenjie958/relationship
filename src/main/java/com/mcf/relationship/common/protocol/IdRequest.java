package com.mcf.relationship.common.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/1 18:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IdRequest extends BaseRequest{
    @Serial
    private static final long serialVersionUID = -1633056322439903941L;
    /**
     * 主键ID
     */
    private Long id;
}
