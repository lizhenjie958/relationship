package com.mcf.relationship.domain.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.relationship.request.RelationshipQueryRequest;
import com.mcf.relationship.controller.relationship.request.RelationshipUpdateRequest;
import com.mcf.relationship.controller.relationship.response.RelationshipDetailResponse;
import com.mcf.relationship.controller.relationship.vo.SimpleRelationshipVO;
import com.mcf.relationship.domain.entity.RelationBO;
import com.mcf.relationship.domain.entity.RelationshipBO;
import com.mcf.relationship.domain.service.RelationshipService;
import com.mcf.relationship.infra.manager.RelationshipManager;
import com.mcf.relationship.infra.mapper.RelationshipMapper;
import com.mcf.relationship.infra.model.RelationshipDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 关系表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-01
 */
@Service
public class RelationshipServiceImp extends ServiceImpl<RelationshipMapper, RelationshipDO> implements RelationshipService {

    @Resource
    private RelationshipManager relationshipManager;

    @Override
    public PageResponse<SimpleRelationshipVO> queryList(RelationshipQueryRequest request) {
        PageResponse<RelationshipBO> relationshipPage = relationshipManager.queryList(request);
        return PageConvertUtil.convertResponse(relationshipPage, relationshipBO -> {
            SimpleRelationshipVO simpleRelationshipVO = new SimpleRelationshipVO();
            simpleRelationshipVO.setId(relationshipBO.getId());
            simpleRelationshipVO.setProtagonist(relationshipBO.getProtagonist());
            simpleRelationshipVO.setPicUrl(relationshipBO.getPicUrl());
            simpleRelationshipVO.setRemark(relationshipBO.getRemark());
            return simpleRelationshipVO;
        });
    }

    @Override
    public Boolean add(RelationshipUpdateRequest request) {
        AssertUtil.checkStringNotBlank(request.getProtagonist(), "请输入主角名称");
        AssertUtil.checkStringNotBlank(request.getPicUrl(), "请上传图片");
        AssertUtil.checkStringNotBlank(request.getRemark(), "请输入主角描述");
        AssertUtil.checkCollectionNotEmpty(request.getRelationList(), "请添加关系");
        RelationshipBO relationship4DB = relationshipManager.selectByProtagonist(request.getUserId(), request.getProtagonist());
        if(Objects.nonNull(relationship4DB)){
            throw new BizException(BizExceptionEnum.PROTAGONIST_EXIST);
        }
        RelationshipBO relationshipBO = new RelationshipBO();
        relationshipBO.setUserId(UserLoginContextUtil.getUserId());
        relationshipBO.setProtagonist(request.getProtagonist());
        relationshipBO.setPicUrl(request.getPicUrl());
        relationshipBO.setRemark(request.getRemark());
        relationshipBO.setRelations(JSONObject.toJSONString(request.getRelationList()));
        relationshipManager.add(relationshipBO);
        return Boolean.TRUE;
    }

    @Override
    public void update(RelationshipUpdateRequest request) {
        AssertUtil.checkObjectNotNull(request.getId(), "ID");
        RelationshipDO relation4DB = baseMapper.selectById(request.getId());
        AssertUtil.checkDataExist(relation4DB, "关系");
        if (StringUtils.isNoneBlank(request.getProtagonist()) &&
                !StringUtils.equals(request.getProtagonist(), relation4DB.getProtagonist())) {
            RelationshipBO relationshipBO = relationshipManager.selectByProtagonist(request.getUserId(), request.getProtagonist());
            if (Objects.nonNull(relationshipBO)) {
                throw new BizException(BizExceptionEnum.PROTAGONIST_EXIST);
            }
        }
        RelationshipBO updateBO = new RelationshipBO();
        updateBO.setId(request.getId());
        updateBO.setProtagonist(request.getProtagonist());
        updateBO.setPicUrl(request.getPicUrl());
        updateBO.setRemark(request.getRemark());
        updateBO.setRelations(JSONObject.toJSONString(request.getRelationList()));
        relationshipManager.update(updateBO);
    }

    @Override
    public RelationshipDetailResponse queryDetail(Long id) {
        AssertUtil.checkObjectNotNull(id, "ID");
        RelationshipDO relation4DB = baseMapper.selectById(id);
        AssertUtil.checkDataExist(relation4DB, "关系");
        RelationshipDetailResponse response = new RelationshipDetailResponse();
        response.setProtagonist(relation4DB.getProtagonist());
        response.setPicUrl(relation4DB.getPicUrl());
        response.setRemark(relation4DB.getRemark());
        List<RelationBO> relationList =  JSONObject.parseObject(relation4DB.getRelations(),new TypeReference<>(){});
        response.setRelationList(relationList);
        return response;
    }
}
