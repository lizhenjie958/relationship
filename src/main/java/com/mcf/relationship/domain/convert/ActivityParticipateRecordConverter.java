package com.mcf.relationship.domain.convert;

import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.controller.activity.response.ParticipateRecordResponse;
import com.mcf.relationship.domain.entity.ActivityParticipateRecordBO;
import com.mcf.relationship.infra.model.ActivityParticipateRecordDO;

/**
 * @Author ZhuPo
 * @date 2026/2/8 09:19
 */
public final class ActivityParticipateRecordConverter {
    public static ActivityParticipateRecordBO do2bo(ActivityParticipateRecordDO activityParticipateRecordDO){
        if(activityParticipateRecordDO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(activityParticipateRecordDO, new ActivityParticipateRecordBO());
    }

    public static ParticipateRecordResponse bo2response(ActivityParticipateRecordBO recordBO){
        if(recordBO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(recordBO, new ParticipateRecordResponse());
    }
}
