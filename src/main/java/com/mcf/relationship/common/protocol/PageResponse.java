package com.mcf.relationship.common.protocol;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class PageResponse<T> implements Serializable {
    private static final long serialVersionUID = 3511240530366982474L;
    private Long total;
    private List<T> list;

    public PageResponse(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }
}
