package com.mcf.relationship.common.util;

/**
 * @author ZhuPo
 * @date 2026/1/30 14:22
 */
public final class SignUtil {
    private static final String PREFIX = "relationship=8905e6ee&timestamp=";
    /**
     * 签名
     * @param signTimestamp
     * @return
     */
    public static String sign(long signTimestamp) {
        AssertUtil.checkObjectNotNull(signTimestamp, "时间戳");
        String signRaw = PREFIX + signTimestamp;
        String sign = MD5Util.md5(signRaw);
        return sign;
    }
}
