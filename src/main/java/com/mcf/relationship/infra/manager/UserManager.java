package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.statistics.request.UserQueryRequest;
import com.mcf.relationship.domain.convert.UserConverter;
import com.mcf.relationship.domain.entity.UserBO;
import com.mcf.relationship.infra.mapper.UserMapper;
import com.mcf.relationship.infra.model.UserDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author ZhuPo
 * @date 2026/2/4 20:10
 */
@Component
public class UserManager {

    @Resource
    private UserMapper userMapper;

    /**
     * 查询用户列表
     * @return
     */
    public PageResponse<UserBO> queryList(UserQueryRequest request){
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        Page<UserDO> userDOPage = userMapper.selectPage(request.page(), queryWrapper);
        return PageConvertUtil.convertPage(userDOPage, UserConverter::do2bo);
    }

    public UserBO getUserByOpenId(String openId) {
        AssertUtil.checkStringNotBlank(openId, "微信OpenId");
        LambdaUpdateWrapper<UserDO> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(UserDO::getWxOpenId, openId);
        UserDO userDO = userMapper.selectOne(queryWrapper, false);
        return UserConverter.do2bo(userDO);
    }

    public UserBO currentUser(){
        Long userId = UserLoginContextUtil.getUserId();
        if(Objects.isNull(userId)){
            throw new BizException(BizExceptionEnum.LOGIN_EXPIRED);
        }
        UserBO userBO = this.getUserByUserId(userId);
        if(Objects.isNull(userBO)){
            throw new BizException(BizExceptionEnum.USER_NOT_EXIST);
        }
        return userBO;
    }

    public UserBO getUserByUserId(Long userId) {
        AssertUtil.checkObjectNotNull(userId, "用户ID");
        UserDO userDO = userMapper.selectById(userId);
        return UserConverter.do2bo(userDO);
    }

    public UserBO saveUser(UserBO userBO) {
        AssertUtil.checkObjectNotNull(userBO.getUsername(), "用户名");
        UserDO userDO = UserConverter.bo2do(userBO);
        userMapper.insert(userDO);
        userBO.setId(userDO.getId());
        return userBO;
    }


    public void updateUser(UserBO userBO) {
        AssertUtil.checkObjectNotNull(userBO.getId(), "用户ID");
        UserDO userDO = UserConverter.bo2do(userBO);
        userMapper.updateById(userDO);
    }
}
