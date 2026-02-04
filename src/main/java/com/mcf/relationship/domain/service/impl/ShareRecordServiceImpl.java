package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.enums.EnableStatusEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.controller.sharerecord.request.ShareRecordQueryRequest;
import com.mcf.relationship.controller.sharerecord.request.ShareRequest;
import com.mcf.relationship.controller.sharerecord.vo.ShareRecordVO;
import com.mcf.relationship.domain.ability.share.ShareAbility;
import com.mcf.relationship.domain.convert.ShareRecordConverter;
import com.mcf.relationship.domain.entity.ShareRecordBO;
import com.mcf.relationship.domain.service.ShareRecordService;
import com.mcf.relationship.infra.manager.ShareRecordManager;
import com.mcf.relationship.infra.mapper.ShareRecordMapper;
import com.mcf.relationship.infra.model.ShareRecordDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-03
 */
@Service
public class ShareRecordServiceImpl extends ServiceImpl<ShareRecordMapper, ShareRecordDO> implements ShareRecordService {

    @Resource
    private List<ShareAbility> shareAbilityList;

    @Resource
    private ShareRecordManager shareRecordManager;

    @Override
    public void addRecord(ShareRequest request) {
        AssertUtil.checkCollectionNotEmpty(shareAbilityList, "分享能力");
        for (ShareAbility shareAbility : shareAbilityList) {
            if (shareAbility.match(request.getSourceType())) {
                 shareAbility.addRecord(request);
            }
        }
        throw new BizException(BizExceptionEnum.SHARE_FAIL);
    }

    @Override
    public PageResponse<ShareRecordVO> queryList(ShareRecordQueryRequest request) {
        PageResponse<ShareRecordBO> shareRecordBOPageResponse = shareRecordManager.queryList(request);
        return PageConvertUtil.convertResponse(shareRecordBOPageResponse, ShareRecordConverter :: bo2vo);
    }

    @Override
    public String queryTargetPath(String shareCode) {
        ShareRecordBO shareRecordBO = shareRecordManager.queryDetail(shareCode);
        if (!EnableStatusEnum.ENABLE.getStatus().equals(shareRecordBO.getEnableStatus())) {
            throw new BizException(BizExceptionEnum.SHARE_DISABLED);
        }
        if(shareRecordBO.getExpireTime().isBefore(LocalDateTime.now())){
            throw new BizException(BizExceptionEnum.SHARE_EXPIRED);
        }
        return shareRecordBO.getTargetPath();
    }
}
