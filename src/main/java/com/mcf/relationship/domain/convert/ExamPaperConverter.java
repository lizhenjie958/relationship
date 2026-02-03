package com.mcf.relationship.domain.convert;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.mcf.relationship.common.dto.ProtagonistInfoDTO;
import com.mcf.relationship.common.dto.QuestionDTO;
import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.controller.exampaper.response.ExamPaperDetailResponse;
import com.mcf.relationship.controller.exampaper.vo.SimpleExamPaperVO;
import com.mcf.relationship.domain.entity.ExamPaperBO;
import com.mcf.relationship.infra.model.ExamPaperDO;

import java.util.List;
import java.util.Objects;

/**
 * @Author ZhuPo
 * @date 2026/2/2 22:35
 */
public final class ExamPaperConverter {
    public static ExamPaperBO do2bo(ExamPaperDO examPaperDO) {
        if (Objects.isNull(examPaperDO)){
            return null;
        }
        ExamPaperBO examPaperBO = BeanCopyUtil.copyForNew(examPaperDO, new ExamPaperBO());
        examPaperBO.setQuestionDTOList(JSONObject.parseObject(examPaperBO.getQuestions(),new TypeReference<List<QuestionDTO>>(){}));
        examPaperBO.setProtagonistInfoDTO(JSONObject.parseObject(examPaperBO.getProtagonistInfo(), ProtagonistInfoDTO.class));
        return examPaperBO;
    }

    public static ExamPaperDO bo2do(ExamPaperBO examPaperBO) {
        if (Objects.isNull(examPaperBO)){
            return null;
        }
        ExamPaperDO examPaperDO = BeanCopyUtil.copyForNew(examPaperBO, new ExamPaperDO());
        examPaperDO.setQuestions(JSONObject.toJSONString(examPaperBO.getQuestionDTOList()));
        examPaperDO.setProtagonistInfo(JSONObject.toJSONString(examPaperBO.getProtagonistInfoDTO()));
        return examPaperDO;
    }

    public static ExamPaperDetailResponse bo2Resp(ExamPaperBO examPaperBO){
        if (Objects.isNull(examPaperBO)){
            return null;
        }
        ExamPaperDetailResponse examPaperDetailResponse = BeanCopyUtil.copyForNew(examPaperBO, new ExamPaperDetailResponse());
        return examPaperDetailResponse;
    }

    public static SimpleExamPaperVO bo2SimpleResp(ExamPaperBO response) {
        if (Objects.isNull(response)){
            return null;
        }
        return BeanCopyUtil.copyForNew(response, new SimpleExamPaperVO());
    }
}
