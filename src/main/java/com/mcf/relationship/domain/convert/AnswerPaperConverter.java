package com.mcf.relationship.domain.convert;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.mcf.relationship.common.dto.AnswerQuestionDTO;
import com.mcf.relationship.common.dto.ProtagonistInfoDTO;
import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.controller.answerpaper.response.AnswerPaperDetailResponse;
import com.mcf.relationship.controller.answerpaper.vo.SimpleAnswerPaperVO;
import com.mcf.relationship.domain.entity.AnswerPaperBO;
import com.mcf.relationship.infra.model.AnswerPaperDO;

import java.util.List;

/**
 * @author ZhuPo
 * @date 2026/2/4 14:05
 */
public final class AnswerPaperConverter {

    /**
     * BO 转 DO
     *
     * @param answerPaperBO
     * @return
     */
    public static AnswerPaperDO boToDo(AnswerPaperBO answerPaperBO) {
        if (answerPaperBO == null) {
            return null;
        }
        AnswerPaperDO answerPaperDO = BeanCopyUtil.copyForNew(answerPaperBO, new AnswerPaperDO());
        if(answerPaperBO.getProtagonistInfoDTO() != null){
            answerPaperDO.setProtagonistInfo(JSONObject.toJSONString(answerPaperBO.getProtagonistInfoDTO()));
        }
        if(answerPaperBO.getAnswerQuestionDTOList() != null){
            answerPaperDO.setAnswers(JSONObject.toJSONString(answerPaperBO.getAnswerQuestionDTOList()));
        }
        return answerPaperDO;
    }

    /**
     * DO 转 BO
     *
     * @param answerPaperDO
     * @return
     */
    public static AnswerPaperBO doToBo(AnswerPaperDO answerPaperDO) {
        if (answerPaperDO == null) {
            return null;
        }
        AnswerPaperBO answerPaperBO = BeanCopyUtil.copyForNew(answerPaperDO, new AnswerPaperBO());
        answerPaperBO.setProtagonistInfoDTO(JSONObject.parseObject(answerPaperDO.getProtagonistInfo(), ProtagonistInfoDTO.class));
        answerPaperBO.setAnswerQuestionDTOList(JSONObject.parseObject(answerPaperDO.getAnswers(),new TypeReference<List<AnswerQuestionDTO>>(){}));
        return answerPaperBO;
    }


    /**
     * BO 转 VO
     *
     * @param answerPaperBO
     * @return
     */
    public static SimpleAnswerPaperVO bo2vo(AnswerPaperBO answerPaperBO){
        if (answerPaperBO == null){
            return null;
        }
        SimpleAnswerPaperVO simpleAnswerPaperVO = BeanCopyUtil.copyForNew(answerPaperBO, new SimpleAnswerPaperVO());
        return simpleAnswerPaperVO;
    }


    public static AnswerPaperDetailResponse bo2detail(AnswerPaperBO answerPaperBO){
        if (answerPaperBO == null){
            return null;
        }
        return BeanCopyUtil.copyForNew(answerPaperBO, new AnswerPaperDetailResponse());
    }
}
