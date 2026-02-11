package com.mcf.relationship.infra.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mcf.relationship.common.enums.ActivityStatusEnum;
import com.mcf.relationship.common.enums.EnableStatusEnum;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.enums.TimeUnitEnum;
import com.mcf.relationship.domain.convert.MemberConverter;
import com.mcf.relationship.domain.entity.ActivityBO;
import com.mcf.relationship.domain.entity.MemberAccessRecordBO;
import com.mcf.relationship.domain.entity.MemberBO;
import com.mcf.relationship.infra.mapper.MemberMapper;
import com.mcf.relationship.infra.model.MemberDO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @Author ZhuPo
 * @date 2026/2/8 18:10
 */
@Component
public class MemberManager {
    @Resource
    private MemberMapper memberMapper;

    @Resource
    private ActivityManager activityManager;

    @Resource
    private MemberAccessRecordManager memberAccessRecordManager;

    /**
     * 充值
     * @param userId
     * @param activityId
     * @param accessReceipt
     */
    @Transactional(rollbackFor = Exception.class)
    public void recharge(Long userId, Long activityId, String accessReceipt){


        AssertUtil.checkObjectNotNull(userId, "用户ID");
        AssertUtil.checkObjectNotNull(activityId, "活动ID");
        AssertUtil.checkObjectNotNull(accessReceipt, "兑换凭证");

        ActivityBO activityBO = activityManager.getActivity(activityId);
        if(activityBO == null || !ActivityStatusEnum.START.getStatus().equals(activityBO.getActivityStatus())){
            return;
        }

        MemberBO member = this.queryMember(userId);

        Integer timeUnitType = activityBO.getRewardUnitType();
        Integer amount = activityBO.getReward();
        String channelCode = activityBO.getChannelCode();
        LocalDate now = LocalDate.now();
        ChronoUnit chronoUnit = TimeUnitEnum.getChronoUnitByUnit(timeUnitType);

        if(member == null){
            MemberDO saveMemberDO = new MemberDO();
            saveMemberDO.setUserId(userId);
            saveMemberDO.setEnableTime(now);
            LocalDate expireDate = now.plus(amount, chronoUnit).minusDays(1);
            saveMemberDO.setExpireTime(expireDate);
            saveMemberDO.setEnableStatus(EnableStatusEnum.ENABLE.getStatus());
            memberMapper.insert(saveMemberDO);
        }else{
            MemberDO memberDO = new MemberDO();
            memberDO.setId(member.getId());
            memberDO.setEnableStatus(EnableStatusEnum.ENABLE.getStatus());
            if (member.getExpireTime().isBefore(now)) {
                memberDO.setEnableTime(now);
                memberDO.setExpireTime(now.plus(amount, chronoUnit).minusDays(1));
            }else {
                memberDO.setExpireTime(member.getExpireTime().plus(amount, chronoUnit));
            }
            memberMapper.updateById(memberDO);
        }

        // 兑换记录
        MemberAccessRecordBO memberRecordBO = new MemberAccessRecordBO();
        memberRecordBO.setUserId(userId);
        memberRecordBO.setAccessChannelCode(channelCode);
        memberRecordBO.setAccessReceipt(accessReceipt);
        memberRecordBO.setAccessUnitType(timeUnitType);
        memberRecordBO.setAccessValue(amount);

        memberAccessRecordManager.addRecord(memberRecordBO);
    }


    public MemberBO queryMember(Long userId){
        AssertUtil.checkObjectNotNull(userId, "用户ID");
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberDO::getUserId, userId);
        MemberDO memberDO = memberMapper.selectOne(queryWrapper);
        return MemberConverter.do2bo(memberDO);
    }
}
