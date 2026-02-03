package com.mcf.relationship.controller.sharerecord;

import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.sharerecord.request.CompleteShareRequest;
import com.mcf.relationship.controller.sharerecord.request.ShareRecordQueryRequest;
import com.mcf.relationship.controller.sharerecord.request.ShareRequest;
import com.mcf.relationship.controller.sharerecord.vo.ShareRecordVO;
import com.mcf.relationship.domain.service.ShareRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2026-02-03
 */
@RestController
@RequestMapping("/shareRecord")
public class ShareRecordController {

    @Resource
    private ShareRecordService shareRecordService;

    @PostMapping("/readyShare")
    public McfResult<String> readyShare(@RequestBody ShareRequest request){
        String shareCode = shareRecordService.readyShare(request);
        return McfResult.success(shareCode);
    }

    @PostMapping("/completeShare")
    public McfResult<Void> completeShare(@RequestBody CompleteShareRequest request){
        shareRecordService.completeShare(request);
        return McfResult.success();
    }

    @PostMapping("/queryList")
    public McfResult<PageResponse<ShareRecordVO>> queryList(@RequestBody ShareRecordQueryRequest request){
        PageResponse<ShareRecordVO> response = shareRecordService.queryList(request);
        return McfResult.success(response);
    }

    @PostMapping("/queryTargetPath")
    public McfResult<String> queryTargetPath(@RequestBody ShareRecordQueryRequest request){
        String shareCode = shareRecordService.queryTargetPath(request.getShareCode());
        return McfResult.success(shareCode);
    }
}
