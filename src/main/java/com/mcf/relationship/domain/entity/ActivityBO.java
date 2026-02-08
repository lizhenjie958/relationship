package com.mcf.relationship.domain.entity;

import com.mcf.relationship.infra.model.ActivityDO;
import lombok.Data;

import java.io.Serial;

/**
 * @Author ZhuPo
 * @date 2026/2/7 22:22
 */
@Data
public class ActivityBO extends ActivityDO {
    @Serial
    private static final long serialVersionUID = 4506907022417856238L;
    /**
     * 奖品单位
     */
    private String rewardUnit;
}
