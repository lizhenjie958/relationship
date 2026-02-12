package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mcf.relationship.common.enums.RedeemStatusEnum;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.domain.convert.MemberRedeemConverter;
import com.mcf.relationship.domain.entity.MemberRedeemBO;
import com.mcf.relationship.infra.mapper.MemberRedeemMapper;
import com.mcf.relationship.infra.model.MemberRedeemDO;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author ZhuPo
 * @date 2026/2/8 10:57
 */
@Component
public class MemberRedeemManager {

    @Resource
    private MemberRedeemMapper memberRedeemMapper;

    /**
     * 查询兑换码
     * @param redeemCode
     * @return
     */
    public MemberRedeemBO queryRedeem(String redeemCode) {
        MemberRedeemDO redeemDO = this.getRedeemDO(redeemCode);
        return MemberRedeemConverter.do2bo(redeemDO);
    }

    /**
     * 获取兑换码
     * @param redeemCode
     * @return
     */
    private MemberRedeemDO getRedeemDO(String redeemCode) {
        AssertUtil.checkStringNotBlank(redeemCode, "兑换码");
        LambdaQueryWrapper<MemberRedeemDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberRedeemDO::getRedeemCode, redeemCode);
        return memberRedeemMapper.selectOne(queryWrapper);
    }

    /**
     * 用户兑换
     * @param userId
     * @param redeemCode
     * @return
     */
    public boolean useRedeem(Long userId, String redeemCode) {
        int updateResult = memberRedeemMapper.update(null, new LambdaUpdateWrapper<MemberRedeemDO>()
                .eq(MemberRedeemDO::getRedeemCode, redeemCode)
                .eq(MemberRedeemDO::getRedeemStatus, RedeemStatusEnum.WAIT_USE.getStatus())
                .set(MemberRedeemDO::getRedeemStatus, RedeemStatusEnum.USED.getStatus())
                .set(MemberRedeemDO::getUserId, userId)
                .set(MemberRedeemDO::getRedeemTime, LocalDateTime.now()));
        return updateResult > 0;
    }

}
