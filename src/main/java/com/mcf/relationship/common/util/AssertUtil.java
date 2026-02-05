package com.mcf.relationship.common.util;

import com.mcf.relationship.common.enums.SysExceptionEnum;
import com.mcf.relationship.common.exception.SysException;
import io.micrometer.common.util.StringUtils;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author ZhuPo
 * @date 2026/1/30 14:23
 */
public class AssertUtil {
    /**
     * 断言对象不为空
     *
     * @param obj
     * @param msg
     */
    public static void checkObjectNotNull(Object obj, String msg) {
        if (Objects.isNull(obj)) {
            throw new SysException(SysExceptionEnum.PARAM_EMPTY, msg);
        }
    }

    /**
     * 断言集合不为空
     *
     * @param coll 集合
     * @param msg 错误提示
     */
    public static void checkCollectionNotEmpty(Collection<?> coll, String msg) {
        if (coll == null || coll.isEmpty()) {
            throw new SysException(SysExceptionEnum.PARAM_EMPTY, msg);
        }
    }

    /**
     * 断言map不为空
     *
     * @param coll 集合
     * @param msg 错误提示
     */
    public static void checkMapNotEmpty(Map<?, ?> coll, String msg) {
        if (coll == null || coll.isEmpty()) {
            throw new SysException(SysExceptionEnum.PARAM_EMPTY, msg);
        }
    }

    /**
     * 断言数据存在
     *
     * @param obj
     * @param msg
     */
    public static void checkDataExist(Object obj, String msg) {
        if (Objects.isNull(obj)) {
            throw new SysException(SysExceptionEnum.DATA_NOT_EXIST, msg);
        }
    }

    /**
     * 断言对象数据不存在
     *
     * @param obj
     * @param msg
     */
    public static void checkDataNotExist(Object obj, String msg) {
        if (Objects.nonNull(obj)) {
            throw new SysException(SysExceptionEnum.DATA_EXIST, msg);
        }
    }

    /**
     * 断言字符串不为空
     *
     * @param str
     * @param msg
     */
    public static void checkStringNotBlank(String str, String msg) {
        if (StringUtils.isBlank(str)) {
            throw new SysException(SysExceptionEnum.PARAM_EMPTY, msg);
        }
    }

    /**
     * 断言字符串最小长度
     *
     * @param str
     * @param msg
     */
    public static void checkStringMinSize(String str, String msg, int min) {
        checkStringNotBlank(str, msg);
        if (str.length() < min) {
            throw new SysException(SysExceptionEnum.PARAM_LESS_THAN_MIX_VALUE, msg, min);
        }
    }

    /**
     * 断言字符串最大长度
     *
     * @param str
     * @param msg
     */
    public static void checkStringMaxSize(String str, String msg, int max) {
        checkStringNotBlank(str, msg);
        if (str.length() > max) {
            throw new SysException(SysExceptionEnum.PARAM_GREATER_THAN_MAX_VALUE, msg, max);
        }
    }

    /**
     * 断言集合最小长度
     *
     * @param collection
     * @param msg
     */
    public static void checkCollectionMinSize(Collection<?> collection, String msg, int min) {
        checkCollectionNotEmpty(collection, msg);
        if (collection.size() < min) {
            throw new SysException(SysExceptionEnum.PARAM_LESS_THAN_MIX_VALUE, msg, min);
        }
    }

    /**
     * 断言集合最大长度
     *
     * @param collection
     * @param msg
     */
    public static void checkCollectionMaxSize(Collection<?> collection, String msg, int max) {
        checkCollectionNotEmpty(collection, msg);
        if (collection.size() > max) {
            throw new SysException(SysExceptionEnum.PARAM_GREATER_THAN_MAX_VALUE, msg, max);
        }
    }
}
