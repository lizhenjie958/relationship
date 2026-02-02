package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mcf.relationship.common.consts.CharConst;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.controller.relationship.request.RelationshipQueryRequest;
import com.mcf.relationship.domain.convert.RelationshipConverter;
import com.mcf.relationship.domain.entity.RelationshipBO;
import com.mcf.relationship.infra.mapper.RelationshipMapper;
import com.mcf.relationship.infra.model.RelationshipDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ZhuPo
 * @date 2026/2/2 17:02
 */
@Component
public class RelationshipManager {
    @Resource
    private RelationshipMapper relationshipMapper;

    public PageResponse<RelationshipBO> queryList(RelationshipQueryRequest request) {
        LambdaUpdateWrapper<RelationshipDO> queryMapper = new LambdaUpdateWrapper<>();
        if (StringUtils.isNoneBlank(request.getProtagonist())){
            queryMapper.like(RelationshipDO::getProtagonist, CharConst.PERCENT + request.getProtagonist() + CharConst.PERCENT);
        }
        queryMapper.eq(RelationshipDO::getUserId, request.getUserId());
        Page<RelationshipDO> relationshipPage = relationshipMapper.selectPage(request.page(), queryMapper);
        return PageConvertUtil.convertPage(relationshipPage, RelationshipConverter::do2bo);
    }

    public Boolean add(RelationshipBO relationshipBO) {
        AssertUtil.checkStringNotBlank(relationshipBO.getProtagonist(), "请输入主角名称");
        AssertUtil.checkStringNotBlank(relationshipBO.getPicUrl(), "请上传图片");
        AssertUtil.checkStringNotBlank(relationshipBO.getRemark(), "请输入主角描述");
        AssertUtil.checkCollectionNotEmpty(relationshipBO.getRelationDTOList(), "请添加关系");
        RelationshipDO relationshipDO = RelationshipConverter.bo2do(relationshipBO);
        relationshipMapper.insert(relationshipDO);
        return Boolean.TRUE;
    }

    public void update(RelationshipBO relationshipBO) {
        AssertUtil.checkObjectNotNull(relationshipBO.getId(), "ID");
        RelationshipDO relationshipDO = RelationshipConverter.bo2do(relationshipBO);
        relationshipMapper.updateById(relationshipDO);
    }

    public RelationshipBO queryDetail(Long id) {
        AssertUtil.checkObjectNotNull(id, "ID");
        RelationshipDO relation4DB = relationshipMapper.selectById(id);
        AssertUtil.checkDataExist(relation4DB, "关系");
        return RelationshipConverter.do2bo(relation4DB);
    }

    public RelationshipBO selectByProtagonist(Long userId, String name){
        LambdaQueryWrapper<RelationshipDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RelationshipDO::getUserId, userId);
        queryWrapper.eq(RelationshipDO::getProtagonist, name);
        RelationshipDO relationshipDO = relationshipMapper.selectOne(queryWrapper, false);
        return RelationshipConverter.do2bo(relationshipDO);
    }
}
