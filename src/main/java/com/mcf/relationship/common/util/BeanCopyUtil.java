package com.mcf.relationship.common.util;

import org.springframework.beans.BeanUtils;

/**
 * @author ZhuPo
 * @date 2026/2/2 17:19
 */
public final class BeanCopyUtil {

    /**
     * 拷贝对象
     *
     * @param source 源对象
     * @param t      目标对象
     * @param <T>    目标对象类型
     * @return 目标对象
     */
    public static <T> T copyForNew(Object source, T t) {
        BeanUtils.copyProperties(source, t);
        return t;
    }
}
