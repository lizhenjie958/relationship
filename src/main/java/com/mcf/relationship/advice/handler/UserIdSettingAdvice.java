package com.mcf.relationship.advice.handler;

import com.mcf.relationship.common.protocol.BaseRequest;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import java.lang.reflect.Type;

/**
 * 将当前用户ID设置到请求参数中
 * @Author ZhuPo
 * @date 2026/2/2 23:20
 */
@ControllerAdvice
public class UserIdSettingAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        // 检查目标类型是否是 BaseRequest 的子类
        return BaseRequest.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 在这里设置 userId
        if (body instanceof BaseRequest baseRequest) {
            Long userId = UserLoginContextUtil.getUserId();
            baseRequest.setUserId(userId);
        }
        return body;
    }
}
