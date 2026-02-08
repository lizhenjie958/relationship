package com.mcf.relationship.infra.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员授权记录
 * </p>
 *
 * @author baomidou
 * @since 2026-02-08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("r_member_access_record")
public class MemberAccessRecordDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 渠道码
     * @see com.mcf.relationship.common.enums.MemberAccessChannelEnum
     */
    private String accessChannelCode;

    /**
     * 获取的凭证
     *
     * 兑换方式：兑换码
     * 活动方式：活动参与ID
     */
    private String accessReceipt;

    /**
     * 单位类型
     * @see com.mcf.relationship.common.enums.TimeUnitEnum
     */
    private Integer accessUnitType;

    /**
     * 值
     * 奖励获取值
     */
    private Integer accessValue;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
