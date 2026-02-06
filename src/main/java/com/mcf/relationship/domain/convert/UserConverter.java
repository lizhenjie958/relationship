package com.mcf.relationship.domain.convert;

import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.controller.user.response.CurrentUserResponse;
import com.mcf.relationship.domain.entity.UserBO;
import com.mcf.relationship.infra.model.UserDO;

/**
 * @Author ZhuPo
 * @date 2026/2/4 21:32
 */
public final class UserConverter {

    public static UserBO do2bo(UserDO userDO) {
        if (userDO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(userDO, new UserBO());
    }

    public static UserDO bo2do(UserBO userBO) {
        if (userBO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(userBO, new UserDO());
    }

    public static CurrentUserResponse bo2CurrentUser(UserBO userBO){
        if (userBO == null){
            return null;
        }
        CurrentUserResponse currentUser = new CurrentUserResponse();
        currentUser.setUsername(userBO.getUsername());
        currentUser.setUserType(userBO.getUserType());
        currentUser.setUserId(userBO.getId());
        currentUser.setAvatar(userBO.getAvatar());
        return currentUser;
    }
}
