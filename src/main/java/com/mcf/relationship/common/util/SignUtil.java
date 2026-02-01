package com.mcf.relationship.common.util;

import com.mcf.relationship.common.consts.CharConst;
import org.apache.commons.lang3.StringUtils;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
                String pairStr = entry.getKey() + CharConst.EQUAL + entry.getValue();
                signPair.add(pairStr);
            }
        }
        AssertUtil.checkCollectionNotEmpty(signPair,"签名数据对");
        String secondSignRaw = StringUtils.join(signPair, CharConst.AMP);
        String signRaw = PREFIX + secondSignRaw;
        String sign = MD5Util.md5Hex(Base64.getEncoder().encodeToString(signRaw.getBytes(StandardCharsets.UTF_8)));
        return sign;
    }
}
