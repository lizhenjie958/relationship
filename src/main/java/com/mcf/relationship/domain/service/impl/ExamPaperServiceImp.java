package com.mcf.relationship.domain.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.controller.exampaper.request.GenerateExamPaperRequest;
import com.mcf.relationship.controller.exampaper.response.GenerateExamPaperResponse;
import com.mcf.relationship.domain.entity.ProtagonistInfoBO;
import com.mcf.relationship.domain.entity.RelationBO;
import com.mcf.relationship.domain.entity.RelationshipBO;
import com.mcf.relationship.domain.service.ExamPaperService;
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
public class ExamPaperServiceImp extends ServiceImpl<ExamPaperMapper, ExamPaperDO> implements ExamPaperService {

    @Resource
    private RelationshipManager relationshipManager;

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


        return null;
    }

    private ExamPaperDO buildExamPaperDO(GenerateExamPaperRequest request, RelationshipBO relationshipBO) {
        ExamPaperDO examPaperDO = new ExamPaperDO();
        examPaperDO.setName(request.getExamPaperName());
        examPaperDO.setRelationshipId(request.getRelationshipId());
        examPaperDO.setProtagonistInfo(relationshipBO.getProtagonist());

        ProtagonistInfoBO protagonistInfoBO = new ProtagonistInfoBO().setProtagonist(relationshipBO.getProtagonist()).setPicUrl(relationshipBO.getPicUrl());
        examPaperDO.setProtagonistInfo(JSONObject.toJSONString(protagonistInfoBO));
        List<RelationBO> relationBOList = relationshipBO.getRelationBOList();
        return examPaperDO;
    }

}
