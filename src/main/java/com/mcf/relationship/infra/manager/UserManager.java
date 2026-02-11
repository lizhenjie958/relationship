package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.enums.CacheEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.user.request.UserQueryRequest;
import com.mcf.relationship.domain.convert.UserConverter;
import com.mcf.relationship.domain.entity.UserBO;
import com.mcf.relationship.infra.cache.MemcachedRepository;
import com.mcf.relationship.infra.mapper.UserMapper;
import com.mcf.relationship.infra.model.UserDO;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author ZhuPo
 * @date 2026/2/4 20:10
 */
@Component
public class UserManager {

    private static final String USER_UPDATE_TIMES_PREFIX = "user:updateTimes:";

    @Resource
    private UserMapper userMapper;

    @Resource
    private SecondaryCacheManager secondaryCacheManager;

    @Resource
    private MemcachedRepository memcachedRepository;

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
        UserBO userWithCache = getUserWithCache(userId);
        if(userWithCache == null){
            throw new BizException(BizExceptionEnum.USER_NOT_EXIST);
        }
        return userWithCache;
    }

    public UserBO getUserWithCache(Long userId){
        UserBO userBO = secondaryCacheManager.getByCache(CacheEnum.CURRENT_USER, userId + "", () -> this.getUserByUserId(userId));
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

    public UserBO getUserByInviteCode(String inviteCode) {
        AssertUtil.checkStringNotBlank(inviteCode, "邀请码");
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getInviteCode, inviteCode);
        UserDO userDO = userMapper.selectOne(queryWrapper, false);
        return UserConverter.do2bo(userDO);
    }


    public void updateUser(UserBO userBO) {
        AssertUtil.checkObjectNotNull(userBO.getId(), "用户ID");
        UserDO userDO = UserConverter.bo2do(userBO);
        secondaryCacheManager.delCache(CacheEnum.CURRENT_USER, userBO.getId() + "", () -> userMapper.updateById(userDO) > 0);
        String key = USER_UPDATE_TIMES_PREFIX + userBO.getId();
        memcachedRepository.incr(key, 1, 1);
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay().plusDays(1);
        memcachedRepository.setExpireUntil(key, localDateTime);
    }

    /**
     * 获取更新次数
     * @param userId
     * @return
     */
    public Integer getUpdateTimes(Long userId) {
        AssertUtil.checkObjectNotNull(userId, "用户ID");
        String key = USER_UPDATE_TIMES_PREFIX + userId;
        String o = memcachedRepository.get(key);
        return NumberUtils.toInt(o);
    }

    /**
     * 统计邀请数量
     * @param userId
     * @return
     */
    public int statisticsCount(Long userId, LocalDateTime startTime, LocalDateTime endTime){
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO :: getId, userId);
        queryWrapper.between(UserDO :: getRegisterTime, startTime, endTime);
        return userMapper.selectCount(queryWrapper).intValue();
    }
}
