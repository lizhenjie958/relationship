package com.mcf.relationship.domain.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.protocol.IdRequest;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.relationship.request.RelationshipQueryRequest;
import com.mcf.relationship.controller.relationship.request.RelationshipUpdateRequest;
import com.mcf.relationship.controller.relationship.response.RelationshipDetailResponse;
import com.mcf.relationship.controller.relationship.vo.RelationshipVO;
import com.mcf.relationship.domain.entity.RelationshipBO;
import com.mcf.relationship.domain.entity.UserBO;
import com.mcf.relationship.domain.service.RelationshipService;
import com.mcf.relationship.infra.manager.RelationshipManager;
import com.mcf.relationship.infra.manager.UserManager;
import com.mcf.relationship.infra.mapper.RelationshipMapper;
import com.mcf.relationship.infra.model.RelationshipDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class RelationshipServiceImpl extends ServiceImpl<RelationshipMapper, RelationshipDO> implements RelationshipService {

    @Resource
    private RelationshipManager relationshipManager;

    @Resource
    private UserManager userManager;

    @Override
    public PageResponse<RelationshipVO> queryList(RelationshipQueryRequest request) {
        AssertUtil.checkObjectNotNull(request.getType(), "关系类型");
        PageResponse<RelationshipBO> relationshipPage = relationshipManager.queryList(request);
        return PageConvertUtil.convertResponse(relationshipPage, relationshipBO -> {
            RelationshipVO relationshipVO = new RelationshipVO();
            relationshipVO.setId(relationshipBO.getId());
            relationshipVO.setProtagonist(relationshipBO.getProtagonist());
            relationshipVO.setPicUrl(relationshipBO.getPicUrl());
            relationshipVO.setRemark(relationshipBO.getRemark());
            return relationshipVO;
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
        // todo 从用户信息中获取用户名称
        relationshipBO.setUsername("系统用户");
        relationshipBO.setProtagonist(request.getProtagonist());
        relationshipBO.setPicUrl(request.getPicUrl());
        relationshipBO.setRemark(request.getRemark());
        relationshipBO.setRelations(JSONObject.toJSONString(request.getRelationList()));
        relationshipManager.add(relationshipBO);
        return Boolean.TRUE;
    }

    @Override
    public void update(RelationshipUpdateRequest request) {
        RelationshipBO relation4DB = relationshipManager.queryDetail(request.getId());
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
        updateBO.setRelationDTOList(request.getRelationList());
        relationshipManager.update(updateBO);
    }

    @Override
    public RelationshipDetailResponse queryDetail(Long id) {
        AssertUtil.checkObjectNotNull(id, "ID");
        RelationshipBO relation4DB = relationshipManager.queryDetail(id);
        RelationshipDetailResponse response = new RelationshipDetailResponse();
        response.setProtagonist(relation4DB.getProtagonist());
        response.setPicUrl(relation4DB.getPicUrl());
        response.setRemark(relation4DB.getRemark());
        response.setRelationList(relation4DB.getRelationDTOList());
        return response;
    }

    @Override
    public void copy(IdRequest request) {
        AssertUtil.checkObjectNotNull(request.getId(), "关系ID");
        RelationshipBO relation4DB = relationshipManager.queryDetail(request.getId());
        Boolean hasCopy = relationshipManager.judgeUserCopy(UserLoginContextUtil.getUserId(), request.getId());
        if(hasCopy){
            throw new BizException(BizExceptionEnum.HASH_SAME_COPY);
        }
        UserBO userBO = userManager.currentUser();
        RelationshipBO copyRelation = relation4DB.copy(userBO.getUsername());
        relationshipManager.add(copyRelation);
    }

    @Override
    public void delete(Long id) {
        relationshipManager.delete(id, UserLoginContextUtil.getUserId());
    }
}
