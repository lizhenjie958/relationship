package com.mcf.relationship.filter;

import com.mcf.relationship.bo.UserTokenBO;
import com.mcf.relationship.consts.SystemConst;
import com.mcf.relationship.enums.BizExceptionEnum;
import com.mcf.relationship.exception.BizException;
import com.mcf.relationship.util.UserTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private static final long MAX_REQUEST_DELAY_TIME = 1000 * 60 * 10000;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = request.getHeader(SystemConst.TOKEN);
            UserTokenBO decrypt = UserTokenUtil.decrypt(token);
        }catch (Exception ignored){
        }
        throw new BizException(BizExceptionEnum.NEED_LOGIN);
    }
}
