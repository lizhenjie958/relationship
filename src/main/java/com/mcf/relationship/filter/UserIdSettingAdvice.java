package com.mcf.relationship.filter;

import com.mcf.relationship.common.protocol.BaseRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 将当前用户ID设置到请求参数中
 * @Author ZhuPo
 * @date 2026/2/2 23:20
 */
@ControllerAdvice
public class UserIdSettingAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        // 检查目标类型是否是 BaseRequest 的子类
        return BaseRequest.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
                                           Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 在这里设置 userId
        if (body instanceof BaseRequest) {
            BaseRequest baseRequest = (BaseRequest) body;
//            Long userId = UserLoginContextUtil.getUserToken().getUserId();
            baseRequest.setUserId(1L);
        }
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage message, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
