package com.mcf.relationship.controller.sharerecord.request;

import com.mcf.relationship.common.protocol.BaseRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @author ZhuPo
 * @date 2026/2/3 15:45
 */
@Data
public class ShareRequest extends BaseRequest {
    @Serial
    private static final long serialVersionUID = -6941310480061044561L;
    /**
     * 源类型
     * @see com.mcf.relationship.common.enums.ShareSourceTypeEnum
     */
    private Integer sourceType;
    /**
     * 源ID
     * 为简化设计，当前仅仅使用主键ID来控制
     */
    private Long sourceId;
}
