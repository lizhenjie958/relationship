package com.mcf.relationship.controller.sharerecord.vo;

import com.mcf.relationship.common.enums.ShareStatusEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: 分享记录VO
 * @author ZhuPo
 * @date 2026/2/3 17:06
 */
@Data
public class ShareRecordVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3267842484371565583L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 来源名称
     */
    private String sourceName;

    /**
     * 分享状态
     * @see ShareStatusEnum
     */
    private Integer shareStatus;

    /**
     * 停止时间
     */
    private LocalDateTime stopTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 分享时间
     */
    private LocalDateTime shareTime;
}
