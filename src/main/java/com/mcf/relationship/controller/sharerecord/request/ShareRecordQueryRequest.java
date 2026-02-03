package com.mcf.relationship.controller.sharerecord.request;

import com.mcf.relationship.common.protocol.PageRequest;
import lombok.Data;

import java.io.Serial;

/**
 * @author ZhuPo
 * @date 2026/2/3 16:21
 */
@Data
public class ShareRecordQueryRequest extends PageRequest {
    @Serial
    private static final long serialVersionUID = 8095237377444315447L;
    /**
     * 分享类型
     * @see com.mcf.relationship.common.enums.ShareSourceTypeEnum
     */
    private Integer sourceType;

    /**
     * 分享码
     */
    private String shareCode;
}
