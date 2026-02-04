package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.controller.user.request.LoginByWxRequest;
import com.mcf.relationship.controller.user.response.LoginResponse;
import com.mcf.relationship.domain.entity.UserBO;
import com.mcf.relationship.domain.service.UserAuthService;
import com.mcf.relationship.infra.manager.UserManager;
import com.mcf.relationship.infra.mapper.UserMapper;
import com.mcf.relationship.infra.model.UserDO;
import com.mcf.relationship.intergration.wx.WxAccreditManager;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author ZhuPo
 * @date 2026/2/4 20:31
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserAuthService {
    @Resource
    private UserManager userManager;

    @Resource
    private WxAccreditManager wxAccreditManager;

    @Override
    public LoginResponse loginByWx(LoginByWxRequest request) {
        AssertUtil.checkStringNotBlank(request.getOpenIdAuthCode(), "微信OpenId授权Code");
        String openId = wxAccreditManager.getOpenId(request.getOpenIdAuthCode());
        UserBO user4DB = userManager.getUserByOpenId(openId);
        UserBO loginUser = user4DB;
        if (Objects.isNull(user4DB)){
            loginUser = rollUp(openId, request.getInviterId());
        }
        return new LoginResponse(loginUser.getId(), loginUser.generateToken());
    }


    /**
     * 注册
     *
     * @param openId
     * @param inviterId
     * @return
     */
    public UserBO rollUp(String openId, Long inviterId) {
        AssertUtil.checkStringNotBlank(openId, "微信OpenId");
        UserBO user4Save = new UserBO(openId, inviterId);
        UserBO userBO = userManager.saveUser(user4Save);
        if(Objects.isNull(userBO) || Objects.isNull(userBO.getId())){
            throw new BizException(BizExceptionEnum.USER_ROLL_UP_FAILED);
        }
        return userBO;
    }
}
