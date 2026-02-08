package com.mcf.relationship.common.util;

import com.mcf.relationship.common.consts.RandomStrConst;

import java.util.Random;

/**
 * @author ZhuPo
 * @date 2025/8/6 15:35
 */
public final class RandomUtil {
    // 定义易读的小写字母和数字（去掉了 l, o, 0, 1）
    private static final char[] READABLE_CHARS = RandomStrConst.READABLE_CHARS;

    private static final Random random = new Random();

    /**
     * 生成指定长度的易读随机字符串（仅小写字母 + 数字）
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String generateStr(int length) {
        if (length <= 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(READABLE_CHARS[random.nextInt(READABLE_CHARS.length)]);
        }
        return sb.toString();
    }
}
