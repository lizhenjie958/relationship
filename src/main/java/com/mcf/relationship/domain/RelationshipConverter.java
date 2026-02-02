package com.mcf.relationship.domain;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.mcf.relationship.common.util.BeanCopyUtil;
import com.mcf.relationship.domain.entity.RelationBO;
import com.mcf.relationship.domain.entity.RelationshipBO;
import com.mcf.relationship.infra.model.RelationshipDO;

import java.util.List;
import java.util.Objects;

/**
 * @author ZhuPo
 * @date 2026/2/2 17:12
 */
public final class RelationshipConverter {
    public static RelationshipBO do2bo(RelationshipDO relationshipDO) {
        if(Objects.isNull(relationshipDO)){
            return null;
        }
        RelationshipBO relationshipBO = BeanCopyUtil.copyForNew(relationshipDO, new RelationshipBO());
        List<RelationBO> relationBOList =  JSONObject.parseObject(relationshipBO.getRelations(),new TypeReference<>(){});
        relationshipBO.setRelationBOList(relationBOList);
        return relationshipBO;
    }

    public static RelationshipDO bo2do(RelationshipBO relationshipBO) {
        if(Objects.isNull(relationshipBO)){
            return null;
        }
        RelationshipDO relationshipDO = BeanCopyUtil.copyForNew(relationshipBO, new RelationshipDO());
        relationshipDO.setRelations(JSONObject.toJSONString(relationshipDO));
        return relationshipDO;
    }
}
