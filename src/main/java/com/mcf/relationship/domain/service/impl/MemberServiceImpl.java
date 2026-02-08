package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.protocol.BaseRequest;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.member.response.MemberResponse;
import com.mcf.relationship.domain.convert.MemberConverter;
import com.mcf.relationship.domain.entity.MemberBO;
import com.mcf.relationship.domain.service.MemberService;
import com.mcf.relationship.infra.manager.MemberManager;
import com.mcf.relationship.infra.mapper.MemberMapper;
import com.mcf.relationship.infra.model.MemberDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-08
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberDO> implements MemberService {

    @Resource
    private MemberManager memberManager;

    @Override
    public MemberResponse queryMember(BaseRequest request) {
        MemberBO memberBO = memberManager.queryMember(UserLoginContextUtil.getUserId());
        return MemberConverter.bo2resp(memberBO);
    }
}
