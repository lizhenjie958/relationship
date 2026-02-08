package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.controller.sharerecord.request.ShareRecordQueryRequest;
import com.mcf.relationship.domain.convert.ShareRecordConverter;
import com.mcf.relationship.domain.entity.ShareRecordBO;
import com.mcf.relationship.infra.mapper.ShareRecordMapper;
import com.mcf.relationship.infra.model.ShareRecordDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author ZhuPo
 * @date 2026/2/3 15:52
 */
@Component
public class ShareRecordManager {
    @Resource
    private ShareRecordMapper shareRecordMapper;
    public void insert(ShareRecordBO entity) {
        shareRecordMapper.insert(entity);
    }

    /**
     * 查询详情
     * @param shareCode
     * @return
     */
    public ShareRecordBO queryDetail(String shareCode){
        LambdaQueryWrapper<ShareRecordDO> queryWrapper = buildQueryWrapper(shareCode);
        ShareRecordDO shareRecordDO = shareRecordMapper.selectOne(queryWrapper,false);
        if (shareRecordDO == null){
            throw new BizException(BizExceptionEnum.SHARE_NOT_EXIST);
        }
        return ShareRecordConverter.do2bo(shareRecordDO);
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public ShareRecordBO queryById(Long id){
        AssertUtil.checkObjectNotNull(id,"分享ID");
        ShareRecordDO shareRecordDO = shareRecordMapper.selectById(id);
        if (shareRecordDO == null){
            throw new BizException(BizExceptionEnum.SHARE_NOT_EXIST);
        }
        return ShareRecordConverter.do2bo(shareRecordDO);
    }

    /**
     * 查询列表
     * @param request
     * @return
     */
    public PageResponse<ShareRecordBO> queryList(ShareRecordQueryRequest request){
        LambdaQueryWrapper<ShareRecordDO> queryWrapper = new LambdaQueryWrapper<>();
        if(Objects.nonNull(request.getSourceType())){
            queryWrapper.eq(ShareRecordDO::getSourceType, request.getSourceType());
        }
        if(Objects.nonNull(request.getShareCode())){
            queryWrapper.eq(ShareRecordDO::getShareCode, request.getShareCode());
        }
        if(Objects.nonNull(request.getSourceId())){
            queryWrapper.eq(ShareRecordDO::getSourceId, request.getSourceId());
        }
        queryWrapper.orderByDesc(ShareRecordDO::getCreateTime);
        Page<ShareRecordDO> shareRecordDOPage = shareRecordMapper.selectPage(request.page(), queryWrapper);
        return PageConvertUtil.convertPage(shareRecordDOPage, ShareRecordConverter::do2bo);
    }

    private LambdaQueryWrapper<ShareRecordDO> buildQueryWrapper(String shareCode){
        AssertUtil.checkStringNotBlank(shareCode, "分享码");
        LambdaQueryWrapper<ShareRecordDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShareRecordDO::getShareCode, shareCode);
        return queryWrapper;
    }

    /**
     * 更新
     * @param shareRecordDO
     */
    public void updateById(ShareRecordDO shareRecordDO) {
        AssertUtil.checkObjectNotNull(shareRecordDO.getId(), "ID");
        shareRecordMapper.updateById(shareRecordDO);
    }
}
