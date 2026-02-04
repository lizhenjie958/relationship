package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.enums.SystemExceptionEnum;
import com.mcf.relationship.common.exception.SysException;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.user.request.UpdateUserRequest;
import com.mcf.relationship.controller.user.response.CurrentUserResponse;
import com.mcf.relationship.domain.convert.UserConverter;
import com.mcf.relationship.domain.entity.UserBO;
import com.mcf.relationship.domain.service.UserService;
import com.mcf.relationship.infra.manager.UserManager;
import com.mcf.relationship.infra.mapper.UserMapper;
import com.mcf.relationship.infra.model.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2026-01-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Resource
    private UserManager userManager;

    @Override
    public CurrentUserResponse currentUser() {
        UserBO userBO = userManager.currentUser();
        return UserConverter.bo2CurrentUser(userBO);
    }

    @Override
    public void updateUser(UpdateUserRequest request) {
        if(StringUtils.isBlank(request.getAvatar()) && StringUtils.isBlank(request.getUsername())){
            throw new SysException(SystemExceptionEnum.PARAM_EMPTY,"有效");
        }
        UserBO userBO = new UserBO();
        userBO.setId(UserLoginContextUtil.getUserId());
        if(StringUtils.isNotBlank(request.getUsername())){
            userBO.setUsername(request.getUsername());
        }
        if(StringUtils.isNotBlank(request.getAvatar())){
            userBO.setAvatar(request.getAvatar());
        }
        userManager.updateUser(userBO);
    }
}
