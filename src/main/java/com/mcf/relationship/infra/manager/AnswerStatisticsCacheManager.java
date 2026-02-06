package com.mcf.relationship.infra.manager;

import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.domain.entity.AnswerStatisticsHistoryBO;
import com.mcf.relationship.infra.cache.RedisRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

/**
 * 签到缓存管理
 * 使用二级缓存才处理
 *
 * @author ZhuPo
 * @date 2026/2/6 13:02
 */
@Repository
public class AnswerStatisticsCacheManager {
    /**
     * 缓存前缀
     *
     */
    private static final String HISTORY_KEY_PREFIX = "answer:checkin:history:%s:%s";
    private static final String NOW_KEY_PREFIX = "answer:checkin:now:%s:%s";

    @Resource
    private RedisRepository redisRepository;

    /**
     * 保存签到
     *
     * @param userId
     * @param
     */
    public void saveHistoryCheckin(Long userId, LocalDate checkinMonth, AnswerStatisticsHistoryBO statisticsHistoryBO){
        AssertUtil.checkObjectNotNull(userId, "用户ID");
        AssertUtil.checkObjectNotNull(checkinMonth, "签到时间");
        AssertUtil.checkObjectNotNull(statisticsHistoryBO, "签到数据");
        String key = this.formatHistoryKey(userId, checkinMonth);
        redisRepository.setObject(key, statisticsHistoryBO, 62, TimeUnit.DAYS);
    }


    /**
     * 获取签到
     *
     * @param userId
     * @param
     */
    public AnswerStatisticsHistoryBO getHistoryCheckin(Long userId, LocalDate checkinMonth){
        AssertUtil.checkObjectNotNull(userId, "用户ID");
        AssertUtil.checkObjectNotNull(checkinMonth, "签到时间");
        String key = this.formatHistoryKey(userId, checkinMonth);
        AnswerStatisticsHistoryBO historyBO = redisRepository.get(key, AnswerStatisticsHistoryBO.class);
        if (historyBO == null || historyBO.getCheckinDates() == null){
            return new AnswerStatisticsHistoryBO();
        }
        return historyBO;
    }


    /**
     * 保存当前签到
     *
     * @param userId
     * @param
     */
    public void saveNowCheckIn(Long userId, LocalDate checkinDate, Integer checkin){
        AssertUtil.checkObjectNotNull(userId, "用户ID");
        AssertUtil.checkObjectNotNull(checkinDate, "签到时间");
        AssertUtil.checkObjectNotNull(checkin, "签到状态");
        String key = this.formatNowKey(userId, checkinDate);
        redisRepository.setObject(key, checkin, 1, TimeUnit.DAYS);
    }

    /**
     * 查询当前签到
     *
     * @param userId
     * @param
     */
    public Integer getNowCheckIn(Long userId, LocalDate checkinDate){
        AssertUtil.checkObjectNotNull(userId, "用户ID");
        AssertUtil.checkObjectNotNull(checkinDate, "签到时间");
        String key = this.formatNowKey(userId, checkinDate);
        return redisRepository.get(key, Integer.class);
    }

    /**
     * 格式化缓存key
     * @param userId
     * @param checkinMonth
     * @return
     */
    private String formatHistoryKey(Long userId, LocalDate checkinMonth){
        return String.format(HISTORY_KEY_PREFIX, userId, checkinMonth);
    }

    /**
     * 格式化缓存key
     * @param userId
     * @param checkinMonth
     * @return
     */
    private String formatNowKey(Long userId, LocalDate checkinMonth){
        return String.format(HISTORY_KEY_PREFIX, userId, checkinMonth);
    }
}
