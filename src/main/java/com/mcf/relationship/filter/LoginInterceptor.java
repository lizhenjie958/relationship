package com.mcf.relationship.filter;

import com.mcf.relationship.domain.entity.UserTokenBO;
import com.mcf.relationship.consts.SystemConst;
import com.mcf.relationship.enums.BizExceptionEnum;
import com.mcf.relationship.exception.BizException;
import com.mcf.relationship.util.UserTokenUtil;
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
            if(true){
                return true;
            }
            String token = request.getHeader(SystemConst.TOKEN);
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
