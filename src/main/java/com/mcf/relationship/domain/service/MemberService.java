package com.mcf.relationship.domain.service;

import com.mcf.relationship.common.protocol.BaseRequest;
import com.mcf.relationship.controller.member.response.MemberResponse;
import com.mcf.relationship.infra.model.MemberDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-08
 */
public interface MemberService extends IService<MemberDO> {

    /**
     * 查询会员
     *
     * @return
     */
    MemberResponse queryMember(BaseRequest request);
}
