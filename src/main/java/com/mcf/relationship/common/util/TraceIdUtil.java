package com.mcf.relationship.common.util;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author ZhuPo
 * @date 2025/8/22 14:49
 */
public class TraceIdUtil {

    public static final String KEY_TRACE_ID = "traceId";

    public static String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void setTraceId(String traceId) {
        MDC.put(KEY_TRACE_ID, traceId);
    }

    public static void removeTraceId() {
        MDC.remove(KEY_TRACE_ID);
    }

    public static String getTraceId() {
        return MDC.get(KEY_TRACE_ID);
    }
}
