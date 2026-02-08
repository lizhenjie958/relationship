package com.mcf.relationship.schedule;

/**
 *
 * 邀请用户注册结算
 * 活动期间，每日统计一次，跳过已经通过好友邀请获得会员的用户
 *
 * @Author ZhuPo
 * @date 2026/2/7 17:44
 */

import com.mcf.relationship.common.util.RandomUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.HashMap;
import java.util.Map;

@Component
public class InviteUserRollupActivitySettleSchedule {

    private static final PropertyPlaceholderHelper helper =
            new PropertyPlaceholderHelper("${", "}");

    public static String replace(String text, Map<String, String> params) {
        return helper.replacePlaceholders(text, params::get);
    }

    // 使用示例
    public static void main(String[] args) {
        System.err.println(RandomUtil.generateStr(6));

        Map<String, String> params = new HashMap<>();
        params.put("startTime", "2024-01-01");
        params.put("endTime", "2024-12-31");
        params.put("signInDay", "30");
        params.put("quantity", "7");
        params.put("timeUnit", "天");

        String template = "活动时间：${startTime}到${endTime}。活动期间内，累计打卡${signInDay}天，可获得${quantity}${timeUnit}会员。";
        String result = InviteUserRollupActivitySettleSchedule.replace(template, params);
        System.err.println(result);
    }


}
