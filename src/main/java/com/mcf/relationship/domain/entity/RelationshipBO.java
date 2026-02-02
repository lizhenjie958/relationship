package com.mcf.relationship.domain.entity;

import com.mcf.relationship.common.consts.OptionConst;
import com.mcf.relationship.common.enums.QuestionTemplateEnum;
import com.mcf.relationship.infra.model.RelationshipDO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZhuPo
 * @date 2026/2/2 17:04
 */
@Data
public class RelationshipBO extends RelationshipDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1344787020586634503L;

    /**
     * 关系列表转化对象
     * 转化对象必须携带类型BO
     */
    private List<RelationBO> relationBOList;


    public List<String> parseRelationNameList(){
        return this.getRelationBOList().stream().map(RelationBO::getRelation).distinct().toList();
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
     * @param relationshipBO
     * @return
     */
    public List<QuestionBO> generateQuestions(RelationshipBO relationshipBO) {
        List<RelationBO> relationList = relationshipBO.getRelationBOList();
        List<String> relationNameList = relationshipBO.parseRelationNameList();
        List<QuestionBO> questionList = new ArrayList<>(relationList.size());
        for (int i = 1; i <= relationList.size(); i++) {
            RelationBO relationBO = relationList.get(i - 1);

            QuestionBO questionBO = new QuestionBO();
            questionBO.setQuestionNo(i);
            questionBO.setPicUrl(relationBO.getPicUrl());
            questionBO.setTemplateKey(QuestionTemplateEnum.GUESS_RELATION.getKey());
            // 选项构造
            List<OptionBO> optionList = this.parseOptionList(relationNameList, relationBO.getRelation());

            // 找到正确答案选项并标记
            optionList.stream()
                    .filter(optionBO -> optionBO.getValue().equals(relationBO.getRelation()))
                    .findFirst()
                    .ifPresent(correctOption -> {
                        questionBO.setCorrectOptionList(Collections.singletonList(correctOption.getKey()));
                    });
            questionBO.setOptionList(optionList);
        }
        return questionList;
    }



    private List<OptionBO> parseOptionList(List<String> relationNameList, String relationName) {
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


    private List<OptionBO> doGetOptionList(List<String> optionNameList) {
        List<OptionBO> resultList = new ArrayList<>(4);
        for (int i = 1; i <= optionNameList.size(); i++) {
            resultList.add(new OptionBO(OptionConst.NO_CHAR_MAP.get(i), optionNameList.get(i - 1)));
        }
        return resultList;
    }
}
