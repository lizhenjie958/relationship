package com.mcf.relationship.advice.handler;

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
        String token = request.getHeader(SystemConst.TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new BizException(BizExceptionEnum.NEED_LOGIN);
        }
        try {
            UserTokenBO userTokenBO = UserTokenUtil.decrypt(token);
            if (userTokenBO == null || userTokenBO.getUserId() == null) {
                throw new BizException(BizExceptionEnum.NEED_LOGIN);
            }
            UserLoginContextUtil.setUserToken(userTokenBO);
        } catch (BizException e) {
            // 如果解密过程中抛出了具体的业务异常（如Token过期），直接向上抛出，保留原始错误码
            throw e;
        } catch (Exception e) {
            // 记录异常日志，便于排查（如解密失败、JSON格式错误等）
            log.warn("token#check#fail#token:{},error:{}", token, e.getMessage());
            throw new BizException(BizExceptionEnum.NEED_LOGIN);
        }
        return true;
    }
}
