package com.mcf.relationship.domain.entity;

import com.mcf.relationship.common.consts.NumberConst;
import com.mcf.relationship.common.dto.AnswerQuestionDTO;
import com.mcf.relationship.common.dto.ProtagonistInfoDTO;
import com.mcf.relationship.common.dto.QuestionDTO;
import com.mcf.relationship.common.enums.AnswerStatusEnum;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.infra.model.AnswerPaperDO;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ZhuPo
 * @date 2026/2/4 14:02
 */
@Data
public class AnswerPaperBO extends AnswerPaperDO {
    @Serial
    private static final long serialVersionUID = 4751186760215722114L;

    /**
     * 主角信息
     */
    private ProtagonistInfoDTO protagonistInfoDTO;

    /**
     * 答案列表
     */
    private List<AnswerQuestionDTO> answerQuestionDTOList;

    /**
     * 初始化
     * @param examPaperBO
     * @return
     */
    public static AnswerPaperBO init(ExamPaperBO examPaperBO){
        AnswerPaperBO answerPaperBO = new AnswerPaperBO();
        LocalDateTime claimTime = LocalDateTime.now();
        answerPaperBO.setExamPaperId(examPaperBO.getExaminerId());
        answerPaperBO.setExamPaperName(examPaperBO.getName());
        answerPaperBO.setProtagonistInfo(examPaperBO.getProtagonistInfo());
        answerPaperBO.setAnswerStatus(AnswerStatusEnum.ANSWERING.getStatus());

        answerPaperBO.setAnswerId(UserLoginContextUtil.getUserId());

        answerPaperBO.setExaminerId(examPaperBO.getExaminerId());
        answerPaperBO.setExaminerName(examPaperBO.getExaminerName());

        answerPaperBO.setClaimTime(claimTime);
        answerPaperBO.setTimeoutTime(claimTime.plusDays(1));
        return answerPaperBO;
    }

    /**
     * 计算分数
     * 所有分数向下取整，不做小数的处理
     * @param examPaperBO
     * @return
     */
    public Integer calculateScore(ExamPaperBO examPaperBO){
        Map<Integer, List<String>> answerMap = this.getAnswerQuestionDTOList().stream().collect(Collectors.toMap(AnswerQuestionDTO::getQuestionNo, AnswerQuestionDTO::getAnswerOptionList));
        List<QuestionDTO> questionDTOList = examPaperBO.getQuestionDTOList();
        int correctCount = 0;
        int totalCount = questionDTOList.size();
        for (QuestionDTO questionDTO : questionDTOList) {
            List<String> answerOptionList = answerMap.get(questionDTO.getQuestionNo());
            if(CollectionUtils.isEmpty(answerOptionList)){
                continue;
            }
            if(CollectionUtils.isEmpty(questionDTO.getCorrectOptionList())){
                continue;
            }
            if (CollectionUtils.isEqualCollection(questionDTO.getCorrectOptionList(), answerOptionList)) {
                correctCount++;
            }
        }
        if(correctCount <= NumberConst.ZERO){
            return NumberConst.ZERO;
        }
        if(correctCount >= totalCount){
            return NumberConst.HUNDRED;
        }
        return new BigDecimal(correctCount)
                .multiply(new BigDecimal(100))
                .divide(new BigDecimal(totalCount), 2, RoundingMode.HALF_UP) // 保留2位小数避免精度丢失
                .setScale(0, RoundingMode.DOWN) // 向下取整
                .intValue();
    }


    /**
     * 完成答题
     * @param examPaperBO
     * @return
     */
    public AnswerPaperBO completeAnswerPaper(ExamPaperBO examPaperBO) {
        AnswerPaperBO answerPaperBO = new AnswerPaperBO();
        answerPaperBO.setId(this.getId());
        answerPaperBO.setAnswerStatus(AnswerStatusEnum.COMPLETED.getStatus());
        answerPaperBO.setScore(this.calculateScore(examPaperBO));
        answerPaperBO.setCompleteTime(LocalDateTime.now());
        return answerPaperBO;
    }

    /**
     * 放弃答题
     * @return
     */
    public AnswerPaperBO giveUpAnswerPaper() {
        AnswerPaperBO answerPaperBO = new AnswerPaperBO();
        answerPaperBO.setId(this.getId());
        answerPaperBO.setAnswerStatus(AnswerStatusEnum.GIVEN_UP.getStatus());
        answerPaperBO.setGiveUpTime(LocalDateTime.now());
        return answerPaperBO;
    }
}
