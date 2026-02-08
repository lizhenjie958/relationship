package com.mcf.relationship.domain.convert;

import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.domain.entity.MemberRedeemBO;
import com.mcf.relationship.infra.model.MemberRedeemDO;

/**
 * @Author ZhuPo
 * @date 2026/2/8 17:38
 */
public final class MemberRedeemConverter {
    public static MemberRedeemBO do2bo(MemberRedeemDO memberRedeemDO) {
        if(memberRedeemDO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(memberRedeemDO, new MemberRedeemBO());
    }
}
