package com.mcf.relationship.common.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mcf.relationship.common.protocol.PageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ZhuPo
 * @date 2025/8/8 16:23
 */
public final class PageConvertUtil {

    public static <OUT> PageResponse<OUT> emptyPage(){
        return new PageResponse<OUT>(0, new ArrayList<>());
    }

    public static <IN,OUT> PageResponse<OUT> convertPage(Page<IN> page, Function<IN, OUT> convert){
        List<OUT> outList = page.getRecords().stream().map(convert).collect(Collectors.toList());
        return new PageResponse<>(page.getTotal(), outList);
    }

    public static <IN> PageResponse<IN> convertPage(Page<IN> page){
        return new PageResponse<>(page.getTotal(), page.getRecords());
    }

    public static <IN, OUT> PageResponse<OUT> convertResponse(PageResponse<IN> page, Function<IN, OUT> convert) {
        List<OUT> outList = page.getList().stream().map(convert).collect(Collectors.toList());
        return new PageResponse<>(page.getTotal(), outList);
    }
}
