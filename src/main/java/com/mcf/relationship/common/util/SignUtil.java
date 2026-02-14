package com.mcf.relationship.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ZhuPo
 * @date 2026/1/30 14:22
 */
public final class SignUtil {
    private static final String SING_FORMAT = "relationship=y6y4a9xx&timestamp=%s&traceid=%s";
    /**
     * 签名
     * @param signTimestamp
     * @return
     */
    public static String sign(Long signTimestamp, String traceId) {
        if(signTimestamp == null || StringUtils.isBlank(traceId) || traceId.length() != 32){
            return null;
        }
        String signRaw = SING_FORMAT.formatted(signTimestamp,traceId);
        String sign = MD5Util.md5(signRaw);
        return sign;
    }
}
