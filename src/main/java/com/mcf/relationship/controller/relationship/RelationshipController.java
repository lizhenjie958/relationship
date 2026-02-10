package com.mcf.relationship.controller.relationship;

import com.mcf.relationship.common.protocol.IdRequest;
import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.relationship.request.RelationshipQueryRequest;
import com.mcf.relationship.controller.relationship.request.RelationshipUpdateRequest;
import com.mcf.relationship.controller.relationship.response.RelationshipDetailResponse;
import com.mcf.relationship.controller.relationship.vo.RelationshipVO;
import com.mcf.relationship.domain.service.RelationshipService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关系
 */
@RestController
@RequestMapping("/relationship")
public class RelationshipController {
    @Resource
    private RelationshipService relationshipService;

    @PostMapping("queryList")
    public McfResult<PageResponse<RelationshipVO>> queryList(@RequestBody RelationshipQueryRequest request){
        PageResponse<RelationshipVO> response = relationshipService.queryList(request);
        return McfResult.success(response);
    }

    @PostMapping("queryDetail")
    public McfResult<RelationshipDetailResponse> queryDetail(@RequestBody IdRequest request){
        RelationshipDetailResponse response = relationshipService.queryDetail(request.getId());
        return McfResult.success(response);
    }

    @PostMapping("add")
    public McfResult<Void> add(@RequestBody RelationshipUpdateRequest request){
        relationshipService.add(request);
        return McfResult.success();
    }

    @PostMapping("update")
    public McfResult<Void> update(@RequestBody RelationshipUpdateRequest request){
        relationshipService.update(request);
        return McfResult.success();
    }

    @PostMapping("copy")
    public McfResult<Void> copy(@RequestBody IdRequest request){
        relationshipService.copy(request);
        return McfResult.success();
    }
}
