package com.mcf.relationship.controller.member.request;

import com.mcf.relationship.common.protocol.PageRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/7 23:09
 */
@Data
public class AccessRecordQueryRequest extends PageRequest {
    @Serial
    private static final long serialVersionUID = 980998171191956718L;
}
