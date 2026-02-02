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
 * 用户表
 * </p>
 *
 * @author baomidou
 * @since 2026-01-31
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("r_user")
public class UserDO implements Serializable {

    @Serial
    private static final long serialVersionUID = 478252771986542206L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 微信用户ID
     */
    private String wxOpenId;

    /**
     * 邀请人ID
     */
    private Long inviterId;

    /**
     * 注册时间
     */
    private LocalDateTime registerTime;

    /**
     * 1普通用户2会员
     */
    private Integer userType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
