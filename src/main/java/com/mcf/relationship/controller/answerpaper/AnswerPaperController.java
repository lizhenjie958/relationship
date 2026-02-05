package com.mcf.relationship.controller.answerpaper;

import com.mcf.relationship.common.protocol.IdRequest;
import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.answerpaper.request.AnswerPaperQueryRequest;
import com.mcf.relationship.controller.answerpaper.request.CompleteAnswerPaperRequest;
import com.mcf.relationship.controller.answerpaper.request.QueryLatestAnsweringRequest;
import com.mcf.relationship.controller.answerpaper.response.AnswerPaperDetailResponse;
import com.mcf.relationship.controller.answerpaper.response.SimpleAnswerPaperResponse;
import com.mcf.relationship.controller.answerpaper.vo.AnswerPaperVO;
import com.mcf.relationship.domain.service.AnswerPaperService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户答卷表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2026-02-04
 */
@RestController
@RequestMapping("/answerPaper")
public class AnswerPaperController {
    @Resource
    private AnswerPaperService answerPaperService;

    @PostMapping("/queryList")
    public McfResult<PageResponse<AnswerPaperVO>> queryList(@RequestBody AnswerPaperQueryRequest request) {
        PageResponse<AnswerPaperVO> pageResponse = answerPaperService.queryList(request);
        return McfResult.success(pageResponse);
    }

    @PostMapping("/queryDetail")
    public McfResult<AnswerPaperDetailResponse> queryDetail(@RequestBody IdRequest request) {
        AnswerPaperDetailResponse response = answerPaperService.queryDetail(request.getId());
        return McfResult.success(response);
    }

    @PostMapping("/completeAnswer")
    public McfResult<Integer> completeAnswer(@RequestBody CompleteAnswerPaperRequest request) {
        Integer score = answerPaperService.completeAnswer(request);
        return McfResult.success(score);
    }

    @PostMapping("/giveUp")
    public McfResult<Void> giveUp(@RequestBody IdRequest request) {
        answerPaperService.giveUp(request.getId());
        return McfResult.success();
    }

    /**
     * 查询最新的正在答题的试卷
     * 按照过期时间倒排，优先返回
     */
    @PostMapping("/queryLatestAnswering")
    public McfResult<SimpleAnswerPaperResponse> queryLatestAnswering(@RequestBody QueryLatestAnsweringRequest request){
        SimpleAnswerPaperResponse response = answerPaperService.queryLatestAnswering(request);
        return McfResult.success(response);
    }
}
