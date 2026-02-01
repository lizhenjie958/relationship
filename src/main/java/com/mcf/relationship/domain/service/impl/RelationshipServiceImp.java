package com.mcf.relationship.domain.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcf.relationship.common.consts.CharConst;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.PageConvertUtil;
import com.mcf.relationship.common.util.UserLoginContextUtil;
import com.mcf.relationship.controller.relationship.request.RelationshipQueryRequest;
import com.mcf.relationship.controller.relationship.request.RelationshipUpdateRequest;
import com.mcf.relationship.controller.relationship.response.RelationshipDetailResponse;
import com.mcf.relationship.controller.relationship.vo.RelationVO;
import com.mcf.relationship.controller.relationship.vo.SimpleRelationshipVO;
import com.mcf.relationship.domain.service.RelationshipService;
import com.mcf.relationship.infra.mapper.RelationshipMapper;
import com.mcf.relationship.infra.model.RelationshipDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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

    @Override
    public PageResponse<SimpleRelationshipVO> queryList(RelationshipQueryRequest request) {
        LambdaUpdateWrapper<RelationshipDO> queryMapper = new LambdaUpdateWrapper<>();
        if (StringUtils.isNoneBlank(request.getProtagonist())){
            queryMapper.like(RelationshipDO::getProtagonist, CharConst.PERCENT + request.getProtagonist() + CharConst.PERCENT);
        }
        queryMapper.eq(RelationshipDO::getUserId, UserLoginContextUtil.getUserId());
        Page<RelationshipDO> relationshipPage = baseMapper.selectPage(request.page(), queryMapper);
        return PageConvertUtil.convertPage(relationshipPage, relationshipDO -> {
            SimpleRelationshipVO simpleRelationshipVO = new SimpleRelationshipVO();
            simpleRelationshipVO.setId(relationshipDO.getId());
            simpleRelationshipVO.setProtagonist(relationshipDO.getProtagonist());
            simpleRelationshipVO.setPicUrl(relationshipDO.getPicUrl());
            simpleRelationshipVO.setRemark(relationshipDO.getRemark());
            return simpleRelationshipVO;
        });
    }

    @Override
    public Boolean add(RelationshipUpdateRequest request) {
        AssertUtil.checkStringNotBlank(request.getProtagonist(), "请输入主角名称");
        AssertUtil.checkStringNotBlank(request.getPicUrl(), "请上传图片");
        AssertUtil.checkStringNotBlank(request.getRemark(), "请输入主角描述");
        AssertUtil.checkCollectionNotEmpty(request.getRelationList(), "请添加关系");

        RelationshipDO relationshipDO1 = selectByProtagonist(request.getProtagonist());
        if(Objects.nonNull(relationshipDO1)){
            throw new BizException(BizExceptionEnum.PROTAGONIST_EXIST);
        }
        RelationshipDO relationshipDO = new RelationshipDO();
        relationshipDO.setUserId(UserLoginContextUtil.getUserId());
        relationshipDO.setProtagonist(request.getProtagonist());
        relationshipDO.setPicUrl(request.getPicUrl());
        relationshipDO.setRemark(request.getRemark());
        relationshipDO.setRelationList(JSONObject.toJSONString(request.getRelationList()));
        baseMapper.insert(relationshipDO);
        return Boolean.TRUE;
    }

    @Override
    public void update(RelationshipUpdateRequest request) {
        AssertUtil.checkObjectNotNull(request.getId(), "ID");
        RelationshipDO relation4DB = baseMapper.selectById(request.getId());
        AssertUtil.checkDataExist(relation4DB, "关系");
        if (StringUtils.isNoneBlank(request.getProtagonist()) &&
                !StringUtils.equals(request.getProtagonist(), relation4DB.getProtagonist())) {
            RelationshipDO relationshipDO = selectByProtagonist(request.getProtagonist());
            if (Objects.nonNull(relationshipDO)) {
                throw new BizException(BizExceptionEnum.PROTAGONIST_EXIST);
            }
        }

        RelationshipDO updateDO = new RelationshipDO();
        updateDO.setId(request.getId());
        updateDO.setProtagonist(request.getProtagonist());
        updateDO.setPicUrl(request.getPicUrl());
        updateDO.setRemark(request.getRemark());
        updateDO.setRelationList(JSONObject.toJSONString(request.getRelationList()));
        baseMapper.updateById(updateDO);
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
        List<RelationVO> relationList =  JSONObject.parseObject(relation4DB.getRelationList(),new TypeReference<>(){});
        response.setRelationList(relationList);
        return response;
    }

    private RelationshipDO selectByProtagonist(String name){
        LambdaQueryWrapper<RelationshipDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RelationshipDO::getUserId, UserLoginContextUtil.getUserId());
        queryWrapper.eq(RelationshipDO::getProtagonist, name);
        return baseMapper.selectOne(queryWrapper, false);
    }
}
