package com.mcf.relationship.common.util;

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
    public static String sign(long signTimestamp, String traceId) {
        AssertUtil.checkObjectNotNull(signTimestamp, "时间戳");
        String signRaw = SING_FORMAT.formatted(signTimestamp,traceId);
        String sign = MD5Util.md5(signRaw);
        return sign;
    }
}
