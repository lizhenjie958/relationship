package com.mcf.relationship.common.protocol;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
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


    public <T> Page<T> page() {
        return Page.of(pageNo, pageSize);
    }
}
