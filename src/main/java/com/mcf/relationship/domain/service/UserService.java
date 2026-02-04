package com.mcf.relationship.domain.service;

import com.mcf.relationship.controller.user.request.UpdateUserRequest;
import com.mcf.relationship.controller.user.response.CurrentUserResponse;
import com.mcf.relationship.infra.model.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2026-01-31
 */
public interface UserService extends IService<UserDO> {
    CurrentUserResponse currentUser();

    void updateUser(UpdateUserRequest request);
}
