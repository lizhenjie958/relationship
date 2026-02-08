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
 * 
 * </p>
 *
 * @author baomidou
 * @since 2026-02-08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("r_member_redeem")
public class MemberRedeemDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 兑换码
     */
    private String redeemCode;

    /**
     * 兑换状态
     */
    private Integer redeemStatus;

    /**
     * 兑换活动ID
     */
    private Long activityId;

    /**
     * 兑换时间
     */
    private LocalDateTime redeemTime;

    /**
     * 兑换用户ID
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
