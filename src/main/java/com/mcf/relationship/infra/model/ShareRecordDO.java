package com.mcf.relationship.infra.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2026-02-03
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("r_share_record")
public class ShareRecordDO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4096856950961516564L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 启用时间
     */
    private LocalDateTime enableTime;

    /**
     * 停用时间
     */
    private LocalDateTime disableTime;

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

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
