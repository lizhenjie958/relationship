package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.enums.MemberAccessChannelEnum;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.controller.activity.request.CurrentActivityQueryRequest;
import com.mcf.relationship.controller.activity.response.CurrentActivityResponse;
import com.mcf.relationship.domain.convert.ActivityConverter;
import com.mcf.relationship.domain.entity.ActivityBO;
import com.mcf.relationship.domain.service.ActivityService;
import com.mcf.relationship.infra.manager.ActivityManager;
import com.mcf.relationship.infra.mapper.ActivityMapper;
import com.mcf.relationship.infra.model.ActivityDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-07
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityDO> implements ActivityService {

    @Resource
    private ActivityManager activityManager;

    @Override
    public CurrentActivityResponse currentActivity(CurrentActivityQueryRequest request) {
        AssertUtil.checkStringNotBlank(request.getChannelCode(), "活动类型");
        MemberAccessChannelEnum channelEnum = MemberAccessChannelEnum.getByCode(request.getChannelCode());
        if(channelEnum == null){
            return null;
        }
        ActivityBO activityBO = activityManager.queryCurrentActivity(request.getChannelCode());
        return ActivityConverter.do2CurrentResponse(activityBO);
    }
}
