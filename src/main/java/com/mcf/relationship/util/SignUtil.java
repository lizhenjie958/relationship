package com.mcf.relationship.util;

import com.mcf.relationship.consts.CharConst;
import org.apache.commons.lang3.StringUtils;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author ZhuPo
 * @date 2026/1/30 14:22
 */
public class SignUtil {
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
        String signRaw = StringUtils.join(signPair, CharConst.AMP);
        String sign = MD5Util.md5Hex(Base64.getEncoder().encodeToString(signRaw.getBytes(StandardCharsets.UTF_8)));
        return sign;
    }
}
