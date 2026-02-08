package com.mcf.relationship.domain.service;

import com.mcf.relationship.controller.activity.request.CurrentActivityQueryRequest;
import com.mcf.relationship.controller.activity.response.CurrentActivityResponse;
import com.mcf.relationship.infra.model.ActivityDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-07
 */
public interface ActivityService extends IService<ActivityDO> {

    /**
     * 查询当前活动
     * 进行中的活动
     *
     * @param request
     * @return
     */
    CurrentActivityResponse currentActivity(CurrentActivityQueryRequest request);
}
