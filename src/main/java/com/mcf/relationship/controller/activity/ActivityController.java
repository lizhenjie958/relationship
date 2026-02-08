package com.mcf.relationship.controller.activity;

import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.controller.activity.request.CurrentActivityQueryRequest;
import com.mcf.relationship.controller.activity.request.ParticipateActivityRequest;
import com.mcf.relationship.controller.activity.request.ParticipateRecordQueryRequest;
import com.mcf.relationship.controller.activity.response.CurrentActivityResponse;
import com.mcf.relationship.controller.activity.response.ParticipateRecordResponse;
import com.mcf.relationship.domain.service.ActivityParticipateRecordService;
import com.mcf.relationship.domain.service.ActivityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author ZhuPo
 * @date 2026/2/7 22:32
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityParticipateRecordService activityParticipateRecordService;

    /**
     * 查询当前活动
     * 进行中的活动
     *
     * @param request
     * @return
     */
    @PostMapping("/currentActivity")
    public McfResult<CurrentActivityResponse> currentActivity(@RequestBody CurrentActivityQueryRequest request){
        CurrentActivityResponse response = activityService.currentActivity(request);
        return McfResult.success(response);
    }

    /**
     * 查询活动参与记录，返回同时当前的进度
     */
    @PostMapping("/queryParticipateRecord")
    public McfResult<ParticipateRecordResponse> queryParticipateRecord(@RequestBody ParticipateRecordQueryRequest request){
        ParticipateRecordResponse response = activityParticipateRecordService.queryParticipateRecord(request);
        return McfResult.success(response);
    }

    /**
     * 参加活动
     */
    @PostMapping("/participate")
    public McfResult<Void> participate(@RequestBody ParticipateActivityRequest request){
        activityParticipateRecordService.participate(request);
        return McfResult.success();
    }

}
