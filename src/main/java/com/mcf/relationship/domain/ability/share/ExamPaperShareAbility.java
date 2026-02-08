package com.mcf.relationship.domain.ability.share;

import com.mcf.relationship.common.enums.ShareSourceTypeEnum;
import com.mcf.relationship.common.util.RandomUtil;
import com.mcf.relationship.controller.sharerecord.request.ShareRequest;
import com.mcf.relationship.domain.entity.ExamPaperBO;
import com.mcf.relationship.domain.entity.ShareRecordBO;
import com.mcf.relationship.infra.manager.ExamPaperManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ZhuPo
 * @date 2026/2/3 15:56
 */
@Component
public class ExamPaperShareAbility extends ShareAbility{
    private static final String PATH_PREFIX = "/pages/exam-paper-detail/exam-paper-detail?id=";

    @Resource
    private ExamPaperManager examPaperManager;

    @Override
    public void buildShareRecordBO(ShareRequest shareRequest, ShareRecordBO shareRecordBO) {
        ExamPaperBO examPaperBO = examPaperManager.queryDetail(shareRequest.getSourceId());
        shareRecordBO.setTargetPath(PATH_PREFIX + shareRequest.getSourceId());
        shareRecordBO.setSourceName(examPaperBO.getName() + "-" + RandomUtil.generateStr(2));
    }

    @Override
    public boolean match(Integer shareType) {
        return ShareSourceTypeEnum.EXAM_PAPER.getType().equals(shareType);
    }
}
