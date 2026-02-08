package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.controller.member.request.AccessRecordQueryRequest;
import com.mcf.relationship.domain.convert.MemberAccessRecordConverter;
import com.mcf.relationship.domain.entity.MemberAccessRecordBO;
import com.mcf.relationship.infra.mapper.MemberAccessRecordMapper;
import com.mcf.relationship.infra.model.MemberAccessRecordDO;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * @Author ZhuPo
 * @date 2026/2/8 18:51
 */
@Component
public class MemberAccessRecordManager {
    @Resource
    private MemberAccessRecordMapper memberAccessRecordMapper;

    /**
     * 添加会员记录
     * @param recordBO
     */
    public void addRecord(MemberAccessRecordBO recordBO){
        memberAccessRecordMapper.insert(recordBO);
    }

    /**
     * 查询会员记录
     * @param request
     * @return
     */
    public PageResponse<MemberAccessRecordBO> queryList(AccessRecordQueryRequest request){
        LambdaQueryWrapper<MemberAccessRecordDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberAccessRecordDO::getUserId, request.getUserId());
        Page<MemberAccessRecordDO> page = memberAccessRecordMapper.selectPage(request.page(), queryWrapper);
        return PageConvertUtil.convertPage(page, MemberAccessRecordConverter::do2bo);
    }
}
