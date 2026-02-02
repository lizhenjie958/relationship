package com.mcf.relationship.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 为简化数据传输，定义数据传输对象
 *
 * 定义多项传输使用的对象为DTO
 * 定义仅对外显示的属性为VO，分页中常用
 * 定义内部属性为BO
 *
 * @author ZhuPo
 * @date 2026/2/2 16:49
 */
@Data
@Accessors(chain = true)
public class ProtagonistInfoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1752455556281107220L;
    private String protagonist;
    private String picUrl;
}
