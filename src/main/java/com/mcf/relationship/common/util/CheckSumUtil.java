package com.mcf.relationship.common.util;

import com.mcf.relationship.common.consts.RandomStrConst;
import org.apache.commons.lang3.StringUtils;
import java.security.SecureRandom;

/**
 * @Author ZhuPo
 * @date 2026/2/8 10:25
 */
public final class CheckSumUtil {

    // 基础字符集（小写字母+数字，排除易混淆字符）
    private static final char[] BASE_CHARS = RandomStrConst.READABLE_CHARS;
    private static final char[] CONFUSING_CHARS = RandomStrConst.CONFUSING_CHARS;

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int DATA_LENGTH = 9;   // 数据部分长度

    /**
     * 生成10位字符串（包含1位校验码）
     * @return 10位字符串
     */
    public static String generateCode() {
        StringBuilder code = new StringBuilder();

        // 生成9位随机字符
        for (int i = 0; i < DATA_LENGTH; i++) {
            int index = RANDOM.nextInt(BASE_CHARS.length);
            code.append(BASE_CHARS[index]);
        }

        // 计算并添加校验码
        String data = code.toString();
        char checksum = calculateChecksum(data);
        code.append(checksum);

        return code.toString();
    }

    /**
     * 校验字符串
     * @param code
     * @return
     */
    public static boolean isValidCode(String code) {
        if(StringUtils.isBlank( code)){
            return false;
        }
        if (code.length() != DATA_LENGTH + 1) {
            return false;
        }
        // 检查数据部分
        for (int i = 0; i <= DATA_LENGTH; i++) {
            char c = code.charAt(i);
            if (!isValidChar(c)) {
                return false;
            }
        }
        // 检查校验和
        char checksum = calculateChecksum(code.substring(0, DATA_LENGTH));
        return checksum == code.charAt(DATA_LENGTH);
    }


    /**
     * 计算校验码
     * @param data 数据字符串
     * @return 校验字符
     */
    private static char calculateChecksum(String data) {
        int sum = 0;

        // 将字符转换为数值并求和
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            int value = getCharValue(c);
            sum += value * (i + 1);  // 加权求和，使用位置作为权重
        }

        // 取模得到校验码位置
        int checksumIndex = sum % BASE_CHARS.length;
        return BASE_CHARS[checksumIndex];
    }

    /**
     * 获取字符的数值
     * @param c 字符
     * @return 数值（0-35对应0-9a-z）
     */
    private static int getCharValue(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'a' && c <= 'z') {
            return (c - 'a') + 10;
        } else {
            return 0;  // 对于非法字符返回0
        }
    }


    /**
     * 检查字符是否有效
     * @param c 字符
     * @return 是否有效
     */
    private static boolean isValidChar(char c) {
        // 必须是数字或小写字母
        if (!(c >= '0' && c <= '9') && !(c >= 'a' && c <= 'z')) {
            return false;
        }
        // 检查是否在排除列表中
        for (char confusingChar : CONFUSING_CHARS) {
            if (c == confusingChar) {
                return false;
            }
        }
        return true;
    }
}
