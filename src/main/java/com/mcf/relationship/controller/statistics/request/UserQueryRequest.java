package com.mcf.relationship.controller.statistics.request;

import com.mcf.relationship.common.protocol.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author ZhuPo
 * @date 2026/2/5 17:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest {
    @Serial
    private static final long serialVersionUID = 8588514131525635517L;
}
