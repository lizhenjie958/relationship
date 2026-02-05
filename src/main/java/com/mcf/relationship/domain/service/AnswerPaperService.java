package com.mcf.relationship.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.answerpaper.request.AnswerPaperQueryRequest;
import com.mcf.relationship.controller.answerpaper.request.CompleteAnswerPaperRequest;
import com.mcf.relationship.controller.answerpaper.request.QueryLatestAnsweringRequest;
import com.mcf.relationship.controller.answerpaper.response.AnswerPaperDetailResponse;
import com.mcf.relationship.controller.answerpaper.response.SimpleAnswerPaperResponse;
import com.mcf.relationship.controller.answerpaper.vo.AnswerPaperVO;
import com.mcf.relationship.infra.model.AnswerPaperDO;

/**
 * <p>
 * 用户答卷表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-04
 */
public interface AnswerPaperService extends IService<AnswerPaperDO> {
    /**
     * 查询列表
     * @param request
     * @return
     */
    PageResponse<AnswerPaperVO> queryList(AnswerPaperQueryRequest request);

    /**
     * 查询详情
     * @param id
     * @return
     */
    AnswerPaperDetailResponse queryDetail(Long id);

    /**
     * 完成答卷
     * @param request
     * @return
     */
    Integer completeAnswer(CompleteAnswerPaperRequest request);

    /**
     * 放弃答卷
     * @param id
     */
    void giveUp(Long id);

    /**
     * 查询最新的答卷
     * @return
     */
    SimpleAnswerPaperResponse queryLatestAnswering(QueryLatestAnsweringRequest request);
}
