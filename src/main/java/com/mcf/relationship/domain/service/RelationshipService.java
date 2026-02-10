package com.mcf.relationship.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mcf.relationship.common.protocol.IdRequest;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.relationship.request.RelationshipQueryRequest;
import com.mcf.relationship.controller.relationship.request.RelationshipUpdateRequest;
import com.mcf.relationship.controller.relationship.response.RelationshipDetailResponse;
import com.mcf.relationship.controller.relationship.vo.RelationshipVO;
import com.mcf.relationship.infra.model.RelationshipDO;

/**
 * <p>
 * 关系表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-01
 */
public interface RelationshipService extends IService<RelationshipDO> {

    /**
     * 查询列表
     *
     * @param request
     * @return
     */
    PageResponse<RelationshipVO> queryList(RelationshipQueryRequest request);

    /**
     * 添加
     *
     * @param request
     * @return
     */
    Boolean add(RelationshipUpdateRequest request);

    /**
     * 修改
     *
     * @param request
     * @return
     */
    void update(RelationshipUpdateRequest request);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    RelationshipDetailResponse queryDetail(Long id);

    /**
     * 拷贝关系
     * @param request
     */
    void copy(IdRequest request);

    /**
     * 删除关系
     * @param id
     */
    void delete(Long id);
}
