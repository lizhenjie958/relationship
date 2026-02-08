package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.controller.member.request.AccessRecordQueryRequest;
import com.mcf.relationship.controller.member.vo.MemberAccessRecordVO;
import com.mcf.relationship.domain.convert.MemberAccessRecordConverter;
import com.mcf.relationship.domain.entity.MemberAccessRecordBO;
import com.mcf.relationship.domain.service.MemberAccessRecordService;
import com.mcf.relationship.infra.manager.MemberAccessRecordManager;
import com.mcf.relationship.infra.mapper.MemberAccessRecordMapper;
import com.mcf.relationship.infra.model.MemberAccessRecordDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 会员授权记录 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-08
 */
@Service
public class MemberAccessRecordServiceImpl extends ServiceImpl<MemberAccessRecordMapper, MemberAccessRecordDO> implements MemberAccessRecordService {

    @Resource
    private MemberAccessRecordManager memberAccessRecordManager;

    @Override
    public PageResponse<MemberAccessRecordVO> queryAccessRecordList(AccessRecordQueryRequest request) {
        PageResponse<MemberAccessRecordBO> pageResponse = memberAccessRecordManager.queryList(request);
        return PageConvertUtil.convertResponse(pageResponse, MemberAccessRecordConverter::bo2vo);
    }
}
