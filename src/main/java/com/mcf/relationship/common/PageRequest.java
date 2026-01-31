package com.mcf.relationship.common;

import lombok.Data;


@Data
public class PageRequest extends BaseRequest {

    private static final long serialVersionUID = -2602880952720233317L;

    /**
     * 查询页码
     */
    private Integer pageNo = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;

}
