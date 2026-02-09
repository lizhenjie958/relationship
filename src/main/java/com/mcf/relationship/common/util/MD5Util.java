package com.mcf.relationship.common.util;

import com.mcf.relationship.common.enums.SysExceptionEnum;
import com.mcf.relationship.common.exception.SysException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5工具类
 *
 */
public class MD5Util {

    public static final String MD5_ALGORITHM = "MD5";

    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM);
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(messageDigest);
        } catch (Exception e) {
            throw new SysException(SysExceptionEnum.ENCRYPT_FAILED, MD5_ALGORITHM);
        }
    }

    /**
     * 字节数组转十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
