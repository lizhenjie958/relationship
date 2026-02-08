package com.mcf.relationship.infra.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author baomidou
 * @since 2026-02-08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("r_member")
public class MemberDO implements Serializable {


    @Serial
    private static final long serialVersionUID = -5156516460162650007L;

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
     * 会员生效时间
     */
    private LocalDate enableTime;

    /**
     * 会员过期时间
     */
    private LocalDate expireTime;

    /**
     * 生效状态
     */
    private Integer enableStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
