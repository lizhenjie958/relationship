package com.mcf.relationship.infra.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2026-02-07
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("r_activity_participate_record")
public class ActivityParticipateRecordDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 参加时间
     */
    private LocalDateTime participateTime;

    /**
     * 参加状态
     */
    private Integer participateStatus;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 当前指标
     */
    private Integer currentIndicator;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
