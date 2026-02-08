package com.mcf.relationship.domain.convert;

import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.controller.member.response.MemberResponse;
import com.mcf.relationship.domain.entity.MemberBO;
import com.mcf.relationship.infra.model.MemberDO;

/**
 * @Author ZhuPo
 * @date 2026/2/8 18:16
 */
public final class MemberConverter {
    public static MemberBO do2bo(MemberDO memberDO){
        if (memberDO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(memberDO,new MemberBO());
    }

    public static MemberResponse bo2resp(MemberBO memberBO) {
        if(memberBO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(memberBO,new MemberResponse());
    }
}
