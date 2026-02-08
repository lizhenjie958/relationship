package com.mcf.relationship.domain.service;

import com.mcf.relationship.controller.activity.request.ParticipateActivityRequest;
import com.mcf.relationship.controller.activity.request.ParticipateRecordQueryRequest;
import com.mcf.relationship.controller.activity.response.ParticipateRecordResponse;
import com.mcf.relationship.infra.model.ActivityParticipateRecordDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-07
 */
public interface ActivityParticipateRecordService extends IService<ActivityParticipateRecordDO> {

    /**
     * 查询活动参与记录，返回同时当前的进度
     */
    ParticipateRecordResponse queryParticipateRecord(ParticipateRecordQueryRequest request);

    /**
     * 参加活动
     */
    void participate(ParticipateActivityRequest request);
}
