package com.mcf.relationship.domain.entity;

import com.mcf.relationship.common.dto.OptionDTO;
import com.mcf.relationship.common.dto.QuestionDTO;
import com.mcf.relationship.common.dto.RelationDTO;
import com.mcf.relationship.common.enums.QuestionTemplateEnum;
import com.mcf.relationship.common.enums.RelationshipTypeEnum;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.infra.model.RelationshipDO;
import lombok.Data;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZhuPo
 * @date 2026/2/2 17:04
 */
@Data
public class RelationshipBO extends RelationshipDO {
    @Serial
    private static final long serialVersionUID = 1344787020586634503L;

    /**
     * 关系列表转化对象
     * 转化对象必须携带类型BO
     */
    private List<RelationDTO> relationDTOList;


    public List<String> parseRelationNameList(){
        return this.getRelationDTOList().stream().map(RelationDTO::getRelationName).distinct().toList();
    }


    /**
     * 生成问题
     * 图片中的人物是主角的（）
     * A: 爸爸 B: 妈妈 C: 儿子 D: 女儿
     * -- 生成试题的必要条件：关系种类>4 && 人物数量>5
     * -- 全部为单选题，每道题分值为100/题目数量，全部答对，总分100，全部答错，总分0。
     * -- 题目答题有效期一小时
     * -- 试卷有效期一星期
     * -- 试卷生成后，不允许修改和删除
     * @return
     */
    public List<QuestionDTO> generateQuestions() {
        List<RelationDTO> relationList = this.getRelationDTOList();
        List<String> relationNameList = this.parseRelationNameList();
        List<QuestionDTO> questionList = new ArrayList<>(relationList.size());
        for (int i = 1; i <= relationList.size(); i++) {
            RelationDTO relationDTO = relationList.get(i - 1);

            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestionNo(i);
            questionDTO.setPicUrl(relationDTO.getPicUrl());
            questionDTO.setTemplateKey(QuestionTemplateEnum.GUESS_RELATION.getKey());
            // 选项构造
            List<OptionDTO> optionList = this.parseOptionList(relationNameList, relationDTO.getRelationName());

            // 找到正确答案选项并标记
            optionList.stream()
                    .filter(optionDTO -> optionDTO.getValue().equals(relationDTO.getRelationName()))
                    .findFirst()
                    .ifPresent(correctOption -> {
                        questionDTO.setCorrectOptionList(Collections.singletonList(correctOption.getKey()));
                    });
            questionDTO.setOptionList(optionList);
            questionList.add(questionDTO);
        }
        return questionList;
    }



    private List<OptionDTO> parseOptionList(List<String> relationNameList, String relationName) {
        List<String> filteredList = relationNameList.stream()
                .filter(name -> !name.equals(relationName))
                .collect(Collectors.toList());
        // 随机打乱列表并取前3个元素
        Collections.shuffle(filteredList);
        List<String> otherOptionList = filteredList.subList(0, 3);
        otherOptionList.add(relationName);
        // 再次随机打乱
        Collections.shuffle(otherOptionList);
        return doGetOptionList(otherOptionList);
    }


    private List<OptionDTO> doGetOptionList(List<String> optionNameList) {
        List<OptionDTO> resultList = new ArrayList<>(4);
        char c = 'A';
        for (int i = 1; i <= optionNameList.size(); i++, c++) {
            resultList.add(new OptionDTO(String.valueOf(c), optionNameList.get(i - 1)));
        }
        return resultList;
    }

    /**
     * 信息copy
     * @return
     */
    public RelationshipBO copy(String userName){
        RelationshipBO relationshipBO = new RelationshipBO();
        relationshipBO.setProtagonist(this.getProtagonist());
        relationshipBO.setRemark(this.getRemark());
        relationshipBO.setUsername(userName);
        relationshipBO.setUserId(UserLoginContextUtil.getUserId());
        relationshipBO.setRelationDTOList(this.getRelationDTOList());
        relationshipBO.setPicUrl(this.getPicUrl());
        relationshipBO.setType(RelationshipTypeEnum.USER.getType());
        relationshipBO.setCopyId(this.getId());
        return relationshipBO;
    }
}
