package com.mcf.relationship.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mcf.relationship.common.protocol.PageResponse;
import com.mcf.relationship.controller.sharerecord.request.ShareRecordQueryRequest;
import com.mcf.relationship.controller.sharerecord.request.ShareRequest;
import com.mcf.relationship.controller.sharerecord.vo.ShareRecordVO;
import com.mcf.relationship.infra.model.ShareRecordDO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2026-02-03
 */
public interface ShareRecordService extends IService<ShareRecordDO> {

    /**
     * 分享
     * @param request
     * @return
     */
    String addRecord(ShareRequest request);

    /**
     * 查询列表
     * @param request
     * @return
     */
    PageResponse<ShareRecordVO> queryList(ShareRecordQueryRequest request);

    /**
     * 查询分享目标路径
     * @param shareCode
     * @return
     */
    String queryTargetPath(String shareCode);
}
