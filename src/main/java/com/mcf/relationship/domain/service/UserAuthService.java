package com.mcf.relationship.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mcf.relationship.controller.user.request.LoginByWxRequest;
import com.mcf.relationship.controller.user.response.LoginResponse;
import com.mcf.relationship.infra.model.UserDO;

/**
 * @Author ZhuPo
 * @date 2026/2/4 20:29
 */
public interface UserAuthService extends IService<UserDO> {
    LoginResponse loginByWx(LoginByWxRequest request);
}
