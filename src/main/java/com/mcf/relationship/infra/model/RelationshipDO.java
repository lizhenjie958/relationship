package com.mcf.relationship.infra.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
public class RelationshipDO {

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
    private String relationList;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
