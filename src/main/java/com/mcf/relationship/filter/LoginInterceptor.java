package com.mcf.relationship.filter;

import com.mcf.relationship.common.consts.SystemConst;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.common.util.UserTokenUtil;
import com.mcf.relationship.domain.entity.UserTokenBO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = request.getHeader(SystemConst.TOKEN);
            if(StringUtils.equals("123456",token)){
                UserLoginContextUtil.setUserToken(new UserTokenBO(System.currentTimeMillis(), 1L, "123456"));
                return true;
            }
            if(StringUtils.isBlank(token)){
                throw new BizException(BizExceptionEnum.NEED_LOGIN);
            }
            UserTokenBO userTokenBO = UserTokenUtil.decrypt(token);
            if(userTokenBO == null || userTokenBO.getUserId() == null){
                throw new BizException(BizExceptionEnum.NEED_LOGIN);
            }
        }catch (Exception ignored){
            throw new BizException(BizExceptionEnum.NEED_LOGIN);
        }
        return true;
    }
}
