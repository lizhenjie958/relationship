package com.mcf.relationship.controller.sharerecord.request;

import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @author ZhuPo
 * @date 2026/2/3 17:49
 */
@Data
public class CompleteShareRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = 40101875414272122L;
    /**
     * 分享码
     */
    private String shareCode;
}
