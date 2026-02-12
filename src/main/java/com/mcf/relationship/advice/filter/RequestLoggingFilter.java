package com.mcf.relationship.advice.filter;

import com.mcf.relationship.common.consts.SystemConst;
import com.mcf.relationship.common.util.TraceIdUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * 全局请求日志过滤器
 * 用于打印请求的详细信息，包括 URL、参数、Body 以及响应状态和耗时
 *
 * @author Gemini Code Assist
 */
@Slf4j
@Component
@Order(1)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return SystemConst.HEALTH_PATH.equals(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 包装 Request 和 Response 以便多次读取 Body
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            mdc(requestWrapper);
            // 执行过滤器链
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            // 记录日志（在请求处理完成后记录，确保能获取到完整的 Body 和响应状态）
            logRequestDetails(requestWrapper, responseWrapper, duration);

            // 重要：将缓存的响应数据回写到原始响应流中，否则客户端将收到空白响应
            responseWrapper.copyBodyToResponse();
            // 清除用户上下文信息
            TraceIdUtil.removeTraceId();
            UserLoginContextUtil.clear();
        }
    }

    private void logRequestDetails(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long costTime) {
        try {
            String uri = request.getRequestURI();
            String clientIp = getClientIp(request);
            byte[] content = request.getContentAsByteArray();
            String requestBodyStr = "";
            if (content.length > 0) {
                String requestBody = new String(content, StandardCharsets.UTF_8);
                // 简单的去换行处理，避免日志过于冗长
                requestBodyStr = requestBody.replaceAll("[\\r\\n]", "");
            }
            String responseBody = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
            Long userId = UserLoginContextUtil.getOrDefaultId();
            LOGGER.info("uri={}||userId={}||cost={}||request={}||response={}||ip={}", uri, userId, costTime, requestBodyStr, responseBody, clientIp);

        } catch (Exception e) {
            LOGGER.warn("logRequestDetails#parse#fail", e);
        }
    }


    /**
     * 获取客户端真实 IP
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    private void mdc(ContentCachingRequestWrapper request){
        try {
            String traceid = request.getHeader(SystemConst.TRACE_ID);
            TraceIdUtil.setTraceId(traceid);
        } catch (Exception e) {
            log.warn("record#mdc#fail",e);
        }
    }

}