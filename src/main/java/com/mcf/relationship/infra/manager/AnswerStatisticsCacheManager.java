package com.mcf.relationship.infra.manager;

import com.mcf.relationship.common.consts.CharConst;
import com.mcf.relationship.common.enums.CacheEnum;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.domain.entity.AnswerStatisticsHistoryBO;
import com.mcf.relationship.infra.cache.RedisRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

/**
 * 签到缓存管理
 * 三层缓存处理：1级本地，2级Memcached, 3级Redis
 *
 * @author ZhuPo
 * @date 2026/2/6 13:02
 */
@Repository
public class AnswerStatisticsCacheManager {

    @Resource
    private RedisRepository redisRepository;

    @Resource
    private SecondaryCacheManager secondaryCacheManager;

    /**
     * 保存签到
     * 签到数据仅保留90天
     *
     * @param userId
     * @param
     */
    public void saveHistoryCheckin(Long userId, LocalDate checkinMonth, AnswerStatisticsHistoryBO statisticsHistoryBO){
        AssertUtil.checkObjectNotNull(userId, "用户ID");
        AssertUtil.checkObjectNotNull(checkinMonth, "签到时间");
        AssertUtil.checkObjectNotNull(statisticsHistoryBO, "签到数据");
        String key = this.formatKey(userId, checkinMonth);
        secondaryCacheManager.delCache(CacheEnum.ANSWER_CHECK_IN_HISTORY,
                key,
                () -> {
                    redisRepository.setObject(key, statisticsHistoryBO, 90, TimeUnit.DAYS);
                    return true;
                }
        );
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
        String key = this.formatKey(userId, checkinMonth);
        AnswerStatisticsHistoryBO historyBO = secondaryCacheManager.getByCache(
                CacheEnum.ANSWER_CHECK_IN_HISTORY, key,
                () -> redisRepository.get(key, AnswerStatisticsHistoryBO.class)
        );
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
        String key = this.formatKey(userId, checkinDate);
        secondaryCacheManager.delCache(CacheEnum.ANSWER_CHECK_IN_HISTORY,
                key,
                () -> {
                    redisRepository.setObject(key, checkin, 1, TimeUnit.DAYS);
                    return true;
                }
        );
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
        String key = this.formatKey(userId, checkinDate);

        return secondaryCacheManager.getByCache(
                CacheEnum.ANSWER_CHECK_IN_HISTORY, key,
                () -> redisRepository.get(key, Integer.class)
        );
    }

    /**
     * 格式化缓存key
     * @param userId
     * @param checkinMonth
     * @return
     */
    private String formatKey(Long userId, LocalDate checkinMonth){
        return userId + CharConst.COLON + checkinMonth;
    }
}
