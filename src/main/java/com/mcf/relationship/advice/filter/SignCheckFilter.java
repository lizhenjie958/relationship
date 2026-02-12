package com.mcf.relationship.advice.filter;

import com.alibaba.fastjson2.JSONObject;
import com.mcf.relationship.common.consts.SystemConst;
import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.common.util.SignUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * @author ZhuPo
 * @date 2026/1/30 16:05
 */
@Slf4j
@Component
@Order(2)
public class SignCheckFilter extends OncePerRequestFilter {
    private static final long MAX_REQUEST_DELAY_TIME = 1000 * 60 * 2;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return SystemConst.HEALTH_PATH.equals(request.getRequestURI()) || "/".equals(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String sign = request.getHeader(SystemConst.SIGN);
            String timestampStr = request.getHeader(SystemConst.TIMESTAMP);
            long timestamp = Long.parseLong(timestampStr);
            String tarceId = request.getHeader(SystemConst.TRACE_ID);
            String calSign = SignUtil.sign(timestamp,tarceId);
            if(checkRequestTime(timestamp) && StringUtils.equals(sign, calSign)){
                chain.doFilter(request, response);
                return;
            }
        }catch (Exception ignored){
            log.warn("签名认证失败");
        }
        buildSignFail(response);
    }

    private boolean checkRequestTime(Long timestamp) {
        return System.currentTimeMillis() - MAX_REQUEST_DELAY_TIME < timestamp;
    }

    private void buildSignFail(HttpServletResponse response) {
        try {
            McfResult<Object> mcfResult = McfResult.signError();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(JSONObject.toJSONString(mcfResult));
        } catch (Exception e) {
            log.error("buildSign#fail",e);
        }
    }
}
