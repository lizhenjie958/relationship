package com.mcf.relationship.controller.activity.request;

import com.mcf.relationship.common.enums.MemberAccessChannelEnum;
import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;
import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/7 22:36
 */
@Data
public class CurrentActivityQueryRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = 1067286356645749602L;
    /**
     * 渠道码
     * @see MemberAccessChannelEnum
     */
    private String channelCode;
}
