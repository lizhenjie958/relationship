package com.mcf.relationship.domain.service.impl;

import com.mcf.relationship.infra.model.UserDO;
import com.mcf.relationship.infra.mapper.UserMapper;
import com.mcf.relationship.domain.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2026-01-31
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, UserDO> implements UserService {

}
