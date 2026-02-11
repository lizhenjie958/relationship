package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.consts.NumberConst;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.enums.SysExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.exception.SysException;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.user.request.MaintainInviterRequest;
import com.mcf.relationship.controller.user.request.UpdateUserRequest;
import com.mcf.relationship.controller.user.response.CurrentUserResponse;
import com.mcf.relationship.controller.user.response.UserInviterInfoResponse;
import com.mcf.relationship.controller.user.response.UserInfoUpdateCheckResponse;
import com.mcf.relationship.domain.convert.UserConverter;
import com.mcf.relationship.domain.entity.UserBO;
import com.mcf.relationship.domain.service.UserService;
import com.mcf.relationship.infra.manager.UserManager;
import com.mcf.relationship.infra.mapper.UserMapper;
import com.mcf.relationship.infra.model.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

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
            throw new SysException(SysExceptionEnum.PARAM_EMPTY,"有效");
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

    @Override
    public void maintainInviter(MaintainInviterRequest request) {
        AssertUtil.checkStringNotBlank(request.getInviteCode(),"邀请码");
        if (StringUtils.length(request.getInviteCode()) != NumberConst.EIGHT) {
            throw new BizException(BizExceptionEnum.INVITER_NOT_EXIST);
        }
        UserBO userBO = userManager.getUserByInviteCode(request.getInviteCode());
        if(Objects.isNull(userBO)){
            throw new BizException(BizExceptionEnum.INVITER_NOT_EXIST);
        }
        if(userBO.judgeHasInviter()){
            throw new BizException(BizExceptionEnum.USER_HAS_INVITER);
        }
        UserBO updateUser = new UserBO();
        updateUser.setId(UserLoginContextUtil.getUserId());
        updateUser.setInviterId(userBO.getId());
        userManager.updateUser(updateUser);
    }

    @Override
    public UserInfoUpdateCheckResponse getUpdateTimes() {
        Integer updateTimes = userManager.getUpdateTimes(UserLoginContextUtil.getUserId());
        UserInfoUpdateCheckResponse userInfoUpdateCheckResponse = new UserInfoUpdateCheckResponse();
        if(updateTimes != null){
            userInfoUpdateCheckResponse.setUpdateTimes(updateTimes);
        }
        return userInfoUpdateCheckResponse;
    }

    @Override
    public UserInviterInfoResponse queryInviter() {
        UserBO userBO = userManager.currentUser();
        if(userBO.getInviterId() == null || userBO.getInviterId() <= 0){
            return null;
        }
        UserBO inviterUser = userManager.getUserByUserId(userBO.getInviterId());
        if(inviterUser == null){
            return null;
        }
        UserInviterInfoResponse response = new UserInviterInfoResponse();
        response.setInviterId(inviterUser.getInviterId());
        response.setInviterName(inviterUser.getUsername());
        response.setInviteCode(inviterUser.getInviteCode());
        return response;
    }
}
