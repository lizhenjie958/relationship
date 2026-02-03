package com.mcf.relationship.domain.ability.share;

import com.mcf.relationship.common.enums.EnableStatusEnum;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.controller.sharerecord.request.ShareRequest;
import com.mcf.relationship.domain.entity.ShareRecordBO;
import com.mcf.relationship.infra.manager.ShareRecordManager;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author ZhuPo
 * @date 2026/2/3 15:58
 */
public abstract class ShareAbility {

    @Resource
    private ShareRecordManager shareRecordManager;

    public void checkParams(ShareRequest request){
        AssertUtil.checkObjectNotNull(request.getSourceId(),"分享源ID");
        AssertUtil.checkObjectNotNull(request.getSourceType(),"分享源");
        AssertUtil.checkObjectNotNull(request.getShareCode(),"分享码");
    }

    public String addRecord(ShareRequest shareRequest){
        checkParams(shareRequest);
        ShareRecordBO shareRecordBO = buildBase(shareRequest);
        buildShareRecordBO(shareRequest, shareRecordBO);
        shareRecordManager.insert(shareRecordBO);
        return shareRecordBO.getShareCode();
    }

    private ShareRecordBO buildBase(ShareRequest request){
        LocalDateTime now = LocalDateTime.now();
        ShareRecordBO shareRecordBO = new ShareRecordBO();
        shareRecordBO.setSourceId(request.getSourceId());
        shareRecordBO.setSourceType(request.getSourceType());
        shareRecordBO.setShareUserId(request.getUserId());
        shareRecordBO.setEnableStatus(EnableStatusEnum.ENABLE.getStatus());
        shareRecordBO.setEnableTime(now);
        shareRecordBO.setExpireTime(now.plusDays(7));
        return shareRecordBO;
    }

    protected abstract void buildShareRecordBO(ShareRequest shareRequest, ShareRecordBO shareRecordBO);

    public abstract boolean match(Integer shareType);
}
