package com.mcf.relationship.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.dto.ProtagonistInfoDTO;
import com.mcf.relationship.common.dto.QuestionDTO;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.enums.ClaimStatusEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.exampaper.request.ExamPaperQueryRequest;
import com.mcf.relationship.controller.exampaper.request.GenerateExamPaperRequest;
import com.mcf.relationship.controller.exampaper.response.ExamPaperDetailResponse;
import com.mcf.relationship.controller.exampaper.response.GenerateExamPaperResponse;
import com.mcf.relationship.controller.exampaper.vo.ExamPaperVO;
import com.mcf.relationship.domain.convert.ExamPaperConverter;
import com.mcf.relationship.domain.entity.AnswerPaperBO;
import com.mcf.relationship.domain.entity.ExamPaperBO;
import com.mcf.relationship.domain.entity.RelationshipBO;
import com.mcf.relationship.domain.service.ExamPaperService;
import com.mcf.relationship.infra.manager.AnswerPaperManager;
import com.mcf.relationship.infra.manager.ExamPaperManager;
import com.mcf.relationship.infra.manager.RelationshipManager;
import com.mcf.relationship.infra.mapper.ExamPaperMapper;
import com.mcf.relationship.infra.model.ExamPaperDO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 试卷 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-02
 */
@Service
public class ExamPaperServiceImpl extends ServiceImpl<ExamPaperMapper, ExamPaperDO> implements ExamPaperService {

    @Resource
    private RelationshipManager relationshipManager;

    @Resource
    private ExamPaperManager examPaperManager;

    @Resource
    private AnswerPaperManager answerPaperManager;

    /**
     * 生成试卷
     * @param request
     * @return
     */
    @Override
    public GenerateExamPaperResponse generate(GenerateExamPaperRequest request) {
        AssertUtil.checkObjectNotNull(request.getRelationshipId(), "关系ID");
        AssertUtil.checkStringNotBlank(request.getExamPaperName(), "试卷名称");
        RelationshipBO relationshipBO = relationshipManager.queryDetail(request.getRelationshipId());
        List<String> relationList = relationshipBO.parseRelationNameList();
        if(CollectionUtils.isEmpty(relationList) || relationList.size() < 4){
            throw new BizException(BizExceptionEnum.RELATION_NOT_ENOUGH_CHARACTERS,4);
        }
        ExamPaperBO examPaperBO = buildExamPaperDO(request, relationshipBO);
        Long save = examPaperManager.save(examPaperBO);
        GenerateExamPaperResponse response = new GenerateExamPaperResponse();
        response.setId(save);
        return response;
    }

    @Override
    public ExamPaperDetailResponse queryDetail(Long id) {
        ExamPaperBO examPaperBO = examPaperManager.queryDetail(id);
        return ExamPaperConverter.bo2Resp(examPaperBO);
    }

    @Override
    public PageResponse<ExamPaperVO> queryList(ExamPaperQueryRequest request) {
        request.setExaminerId(UserLoginContextUtil.getUserId());
        PageResponse<ExamPaperBO> response = examPaperManager.queryList(request);
        return PageConvertUtil.convertResponse(response, ExamPaperConverter::bo2SimpleResp);
    }

    @Override
    public void delete(Long id) {
        examPaperManager.delete(id);
    }

    @Override
    public Long claim(Long id) {
        AssertUtil.checkObjectNotNull(id,"试卷ID");
        ExamPaperBO examPaperBO = examPaperManager.queryDetail(id);
        if (!ClaimStatusEnum.CLAIM_ALLOWED.getStatus().equals(examPaperBO.getClaimStatus())) {
            throw new BizException(BizExceptionEnum.EXAM_PAPER_NOT_CLAIMABLE);
        }
        AnswerPaperBO answerPaperBO = AnswerPaperBO.init(examPaperBO);
        return answerPaperManager.save(answerPaperBO);
    }

    @Override
    public void changeClaimStatus(Long id, Integer claimStatus) {
        AssertUtil.checkObjectNotNull(id,"试卷ID");
        ClaimStatusEnum claimEnum = ClaimStatusEnum.getClaimStatus(claimStatus);
        AssertUtil.checkObjectNotNull(claimEnum,"认领状态");
        ExamPaperBO examPaperBO = examPaperManager.queryDetail(id);
        AssertUtil.checkDataExist(examPaperBO,"试卷");
        if(claimStatus.equals(examPaperBO.getClaimStatus())){
            return;
        }
        ExamPaperBO updatePaperBO = new ExamPaperBO();
        updatePaperBO.setId(id);
        updatePaperBO.setClaimStatus(claimStatus);
        examPaperManager.updateById(updatePaperBO);
    }

    private ExamPaperBO buildExamPaperDO(GenerateExamPaperRequest request, RelationshipBO relationshipBO) {
        ExamPaperBO examPaperBO = new ExamPaperBO();
        examPaperBO.setName(request.getExamPaperName());
        examPaperBO.setRelationshipId(request.getRelationshipId());
        // 设置主角信息
        examPaperBO.setProtagonistInfoDTO(new ProtagonistInfoDTO().setProtagonist(relationshipBO.getProtagonist()).setPicUrl(relationshipBO.getPicUrl()));
        // 生成问题
        List<QuestionDTO> questionDTOS = relationshipBO.generateQuestions();
        examPaperBO.setExaminerId(relationshipBO.getUserId());
        examPaperBO.setExaminerName(relationshipBO.getUsername());
        examPaperBO.setQuestionDTOList(questionDTOS);
        examPaperBO.setClaimStatus(ClaimStatusEnum.CLAIM_ALLOWED.getStatus());
        return examPaperBO;
    }
}
