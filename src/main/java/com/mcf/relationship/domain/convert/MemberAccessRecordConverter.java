package com.mcf.relationship.domain.convert;

import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.controller.member.vo.MemberAccessRecordVO;
import com.mcf.relationship.domain.entity.MemberAccessRecordBO;
import com.mcf.relationship.infra.model.MemberAccessRecordDO;

/**
 * @Author ZhuPo
 * @date 2026/2/8 18:56
 */
public final class MemberAccessRecordConverter {

    public static MemberAccessRecordBO do2bo(MemberAccessRecordDO recordDO){
        if (recordDO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(recordDO,new MemberAccessRecordBO());
    }

    public static MemberAccessRecordDO bo2do(MemberAccessRecordBO recordBO){
        if (recordBO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(recordBO,new MemberAccessRecordDO());
    }

    public static MemberAccessRecordVO bo2vo(MemberAccessRecordBO recordBO) {
        if(recordBO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(recordBO,new MemberAccessRecordVO());
    }
}
