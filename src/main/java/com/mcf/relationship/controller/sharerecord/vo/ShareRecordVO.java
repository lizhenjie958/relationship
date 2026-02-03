package com.mcf.relationship.controller.sharerecord.vo;

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
     * 分享码
     */
    private String shareCode;

    /**
     * 分享来源类型
     */
    private Integer sourceType;

    /**
     * 来源ID
     */
    private Long sourceId;

    /**
     * 来源名称
     */
    private String sourceName;

    /**
     * 目标路径
     */
    private String targetPath;

    /**
     * 是否启用
     */
    private Integer enableStatus;

    /**
     * 分享状态
     */
    private Integer shareStatus;

    /**
     * 分享完成时间
     */
    private LocalDateTime shareCompleteTime;


    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 分享用户ID
     */
    private Long shareUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
