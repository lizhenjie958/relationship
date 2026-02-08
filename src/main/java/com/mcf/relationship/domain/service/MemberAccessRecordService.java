package com.mcf.relationship.domain.service;

import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.member.request.AccessRecordQueryRequest;
import com.mcf.relationship.controller.member.vo.MemberAccessRecordVO;
import com.mcf.relationship.infra.model.MemberAccessRecordDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员授权记录 服务类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-08
 */
public interface MemberAccessRecordService extends IService<MemberAccessRecordDO> {

    /**
     * 查询会员的获取记录
     *
     * @return
     */
    PageResponse<MemberAccessRecordVO> queryAccessRecordList(AccessRecordQueryRequest request);
}
