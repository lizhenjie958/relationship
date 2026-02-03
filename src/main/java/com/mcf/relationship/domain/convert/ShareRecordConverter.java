package com.mcf.relationship.domain.convert;

import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.controller.sharerecord.vo.ShareRecordVO;
import com.mcf.relationship.domain.entity.ShareRecordBO;
import com.mcf.relationship.infra.model.ShareRecordDO;

import java.util.Objects;

/**
 * @author ZhuPo
 * @date 2026/2/3 16:25
 */
public final class ShareRecordConverter {

    public static ShareRecordBO do2bo(ShareRecordDO shareRecordDO){
        if (Objects.isNull(shareRecordDO)){
            return null;
        }
        return BeanCopyUtil.copyForNew(shareRecordDO, new ShareRecordBO());
    }

    public static ShareRecordVO bo2vo(ShareRecordBO shareRecordBO){
        if (Objects.isNull(shareRecordBO)){
            return null;
        }
        return BeanCopyUtil.copyForNew(shareRecordBO, new ShareRecordVO());
    }
}
