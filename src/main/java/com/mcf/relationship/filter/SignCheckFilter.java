package com.mcf.relationship.filter;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.mcf.relationship.base.McfResult;
import com.mcf.relationship.consts.SystemConst;
import com.mcf.relationship.util.SignUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author ZhuPo
 * @date 2026/1/30 16:05
 */
@Slf4j
@Component
@WebFilter
public class SignCheckFilter implements Filter {
    private static final long MAX_REQUEST_DELAY_TIME = 1000 * 60 * 60 * 24;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        if(!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)){
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            String sign = request.getHeader(SystemConst.SIGN);
            String timestampStr = request.getHeader(SystemConst.TIMESTAMP);
            long timestamp = Long.parseLong(timestampStr);
            RequestWrapper requestWrapper = new RequestWrapper(request);
            String requestBody = StreamUtils.copyToString(requestWrapper.getInputStream(), StandardCharsets.UTF_8);
            requestBody = JSONObject.parseObject(requestBody).toJSONString();
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
            Map<String,Object> requestJson = JSONObject.parseObject(requestBody, new TypeReference<>() {});
            requestJson.put(SystemConst.TIMESTAMP, timestamp);
            String calSign = SignUtil.sign(requestJson);
            if(checkRequestTime(timestamp) && StringUtils.equals(sign, calSign)){
                chain.doFilter(requestWrapper, responseWrapper);
                responseWrapper.copyBodyToResponse();
                return;
            }
        }catch (Exception ignored){
        }
        buildLoginFail(response);
    }

    private boolean checkRequestTime(Long timestamp) {
        return System.currentTimeMillis() - MAX_REQUEST_DELAY_TIME < timestamp;
    }

    private void buildLoginFail(HttpServletResponse response) {
        try {
            McfResult<Object> mcfResult = McfResult.signError();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(JSONObject.toJSONString(mcfResult));
        } catch (IOException e) {

        }
    }
}
