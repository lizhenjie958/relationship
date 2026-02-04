package com.mcf.relationship.controller.exampaper;

import com.mcf.relationship.common.protocol.IdRequest;
import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.exampaper.request.ExamPaperQueryRequest;
import com.mcf.relationship.controller.exampaper.request.GenerateExamPaperRequest;
import com.mcf.relationship.controller.exampaper.response.ExamPaperDetailResponse;
import com.mcf.relationship.controller.exampaper.response.GenerateExamPaperResponse;
import com.mcf.relationship.controller.exampaper.vo.SimpleExamPaperVO;
import com.mcf.relationship.domain.service.ExamPaperService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/examPaper")
public class ExamPaperController {

    @Resource
    private ExamPaperService examPaperService;

    @PostMapping("generate")
    public McfResult<GenerateExamPaperResponse> generate(@RequestBody GenerateExamPaperRequest request) {
        GenerateExamPaperResponse response = examPaperService.generate(request);
        return McfResult.success(response);
    }

    @PostMapping("queryList")
    public McfResult<PageResponse<SimpleExamPaperVO>> queryList(@RequestBody ExamPaperQueryRequest request){
        PageResponse<SimpleExamPaperVO> response = examPaperService.queryList(request);
        return McfResult.success(response);
    }

    @PostMapping("queryDetail")
    public McfResult<ExamPaperDetailResponse> queryDetail(@RequestBody IdRequest request) {
        ExamPaperDetailResponse response = examPaperService.queryDetail(request.getId());
        return McfResult.success(response);
    }

    @PostMapping("delete")
    public McfResult<Void> delete(@RequestBody IdRequest request) {
        examPaperService.delete(request.getId());
        return McfResult.success();
    }

    @PostMapping("claim")
    public McfResult<Long> claim(@RequestBody IdRequest request) {
        Long answerPaperId = examPaperService.claim(request.getId());
        return McfResult.success(answerPaperId);
    }
}
