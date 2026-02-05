package com.mcf.relationship.domain.service;

import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.exampaper.request.ExamPaperQueryRequest;
import com.mcf.relationship.controller.exampaper.request.GenerateExamPaperRequest;
import com.mcf.relationship.controller.exampaper.response.ExamPaperDetailResponse;
import com.mcf.relationship.controller.exampaper.response.GenerateExamPaperResponse;
import com.mcf.relationship.controller.exampaper.vo.ExamPaperVO;
import com.mcf.relationship.infra.model.ExamPaperDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 试卷 服务类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-02
 */
public interface ExamPaperService extends IService<ExamPaperDO> {

    /**
     * 生成试卷
     *
     * @param request
     * @return
     */
    GenerateExamPaperResponse generate(GenerateExamPaperRequest request);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    ExamPaperDetailResponse queryDetail(Long id);

    /**
     * 查询列表
     *
     * @param request
     * @return
     */
    PageResponse<ExamPaperVO> queryList(ExamPaperQueryRequest request);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 认领
     * 返回答卷ID
     *
     * @param id
     */
    Long claim(Long id);
}
