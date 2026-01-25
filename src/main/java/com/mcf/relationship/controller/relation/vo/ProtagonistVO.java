package com.mcf.relationship.controller.relation.vo;

import lombok.Data;

/**
 * @Author ZhuPo
 * @date 2026/1/24 19:23
 */
@Data
public class ProtagonistVO {
    /**
     * 主角ID
     */
    private Long id;
    /**
     * 主角名称
     */
    private String name;
    /**
     * 主角相片缩略图
     */
    private String smallPicUrl;
    /**
     * 主角相片
     */
    private String picUrl;
}
