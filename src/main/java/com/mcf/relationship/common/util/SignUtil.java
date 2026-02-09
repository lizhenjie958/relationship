package com.mcf.relationship.common.util;

import com.mcf.relationship.common.consts.CharConst;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author ZhuPo
 * @date 2026/1/30 14:22
 */
public final class SignUtil {
    private static final String PREFIX = "relationship=8905e6ee&";
    /**
     * 签名
     * @param map
     * @return
     */
    public static String sign(Map<String,Object> map) {
        AssertUtil.checkMapNotEmpty(map,"签名数据");
        TreeMap<String, Object> sortedMap = new TreeMap<>(map);
        List<String> signPair = new ArrayList<>();
        for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
            if(entry.getValue() != null){
                String pairStr = entry.getKey() + CharConst.EQUAL + convertValueToString(entry.getValue());
                signPair.add(pairStr);
            }
        }
        AssertUtil.checkCollectionNotEmpty(signPair,"签名数据对");
        String secondSignRaw = StringUtils.join(signPair, CharConst.AMP);
        String signRaw = PREFIX + secondSignRaw;
        System.err.println(signRaw);
        String sign = MD5Util.md5(signRaw);
        return sign;
    }

    /**
     * 统一处理参数值的字符串转换
     * 针对集合类型，通常约定为逗号分隔，需与前端保持一致
     */
    private static String convertValueToString(Object value) {
        if (value == null) {
            return StringUtils.EMPTY;
        }
        // 如果是集合，使用逗号连接，避免 Java 默认 toString 的 [a, b] 格式
        if (value instanceof Collection) {
            return StringUtils.join((Collection<?>) value, ",");
        }
        // 可以根据需要添加 Date, BigDecimal 等类型的特殊处理
        return String.valueOf(value);
    }
}
