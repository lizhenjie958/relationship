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
 * 关系表
 * </p>
 *
 * @author baomidou
 * @since 2026-02-01
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("r_relationship")
public class RelationshipDO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3408758017318203814L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类型
     * @see com.mcf.relationship.common.enums.RelationshipTypeEnum
     */
    private Integer type;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 主角
     */
    private String protagonist;

    /**
     * 主角头像
     */
    private String picUrl;

    /**
     * 备注
     */
    private String remark;

    /**
     * 关系列表
     */
    private String relations;

    /**
     * 拷贝的关系ID
     */
    private Long copyId;

    /**
     * 是否已删除
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
