package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mcf.relationship.common.enums.YNTypeEnum;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.exampaper.request.ExamPaperQueryRequest;
import com.mcf.relationship.domain.convert.ExamPaperConverter;
import com.mcf.relationship.domain.entity.ExamPaperBO;
import com.mcf.relationship.infra.mapper.ExamPaperMapper;
import com.mcf.relationship.infra.model.ExamPaperDO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @Author ZhuPo
 * @date 2026/2/2 22:12
 */
@Component
public class ExamPaperManager {
    @Resource
    private ExamPaperMapper examPaperMapper;

    public Long save(ExamPaperBO examPaperBO) {
        AssertUtil.checkObjectNotNull(examPaperBO.getExaminerId(),"出题人ID");
        AssertUtil.checkCollectionNotEmpty(examPaperBO.getQuestionDTOList(),"题目信息");
        AssertUtil.checkStringNotBlank(examPaperBO.getName(),"试卷名称");
        AssertUtil.checkObjectNotNull(examPaperBO.getRelationshipId(),"关系ID");
        AssertUtil.checkStringNotBlank(examPaperBO.getExaminerName(),"出题人");
        ExamPaperDO examPaperDO = ExamPaperConverter.bo2do(examPaperBO);
        examPaperMapper.insert(examPaperDO);
        return examPaperDO.getId();
    }

    public ExamPaperBO queryDetail(Long id) {
        AssertUtil.checkObjectNotNull(id,"试卷ID");
        ExamPaperDO examPaperDO = examPaperMapper.selectById(id);
        AssertUtil.checkDataExist(examPaperDO,"试卷");
        return ExamPaperConverter.do2bo(examPaperDO);
    }

    public PageResponse<ExamPaperBO> queryList(ExamPaperQueryRequest request) {
        LambdaQueryWrapper<ExamPaperDO> queryWrapper = new LambdaQueryWrapper<>();
        if(request.getExaminerId() != null){
            queryWrapper.eq(ExamPaperDO::getExaminerId,request.getExaminerId());
        }
        queryWrapper.eq(ExamPaperDO::getDeleted, YNTypeEnum.NO.getCode());
        Page<ExamPaperDO> examPaperDOPage = examPaperMapper.selectPage(request.page(), queryWrapper);
        return PageConvertUtil.convertPage(examPaperDOPage, ExamPaperConverter::do2bo);
    }

    public void delete(Long id) {
        AssertUtil.checkObjectNotNull(id,"试卷ID");
        LambdaQueryWrapper<ExamPaperDO> queryWrapper = new LambdaQueryWrapper<ExamPaperDO>()
                .eq(ExamPaperDO::getId, id)
                .eq(ExamPaperDO::getExaminerId, UserLoginContextUtil.getUserId())
                .eq(ExamPaperDO::getDeleted, YNTypeEnum.NO.getCode());
        examPaperMapper.update(new ExamPaperDO().setDeleted(YNTypeEnum.YES.getCode()), queryWrapper);
    }
}
