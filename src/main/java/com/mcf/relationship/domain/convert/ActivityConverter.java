package com.mcf.relationship.domain.convert;

import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.controller.activity.response.CurrentActivityResponse;
import com.mcf.relationship.domain.entity.ActivityBO;
import com.mcf.relationship.infra.model.ActivityDO;

/**
 * @Author ZhuPo
 * @date 2026/2/8 09:11
 */
public final class ActivityConverter {
    public static ActivityBO do2bo(ActivityDO activityDO){
        if (activityDO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(activityDO,new ActivityBO());
    }

    public static CurrentActivityResponse do2CurrentResponse(ActivityBO activityBO){
        if (activityBO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(activityBO,new CurrentActivityResponse());
    }
}
