package com.mcf.relationship.common.util;

import com.mcf.relationship.common.enums.SysExceptionEnum;
import com.mcf.relationship.common.exception.SysException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author ZhuPo
 * @date 2026/1/30 13:52
 */
public final class EncryptUtil {
    public static final String AES_ALGORITHM = "AES";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final Cipher CIPHER;
    private static final SecretKeySpec SECRET_KEY = new SecretKeySpec("40f45aa1a6f54e5a".getBytes(DEFAULT_CHARSET), AES_ALGORITHM);

    static {
        try {
            CIPHER = Cipher.getInstance(AES_ALGORITHM);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密
     *
     * @param plainText
     * @return
     */
    public static String encrypt(String plainText, String desc) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
            byte[] cipherText = cipher.doFinal(plainText.getBytes(DEFAULT_CHARSET));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            throw new SysException(SysExceptionEnum.ENCRYPT_FAILED, desc);
        }
    }

    /**
     * 解密
     *
     * @param cipherText
     * @return
     */
    public static String decrypt(String cipherText, String desc) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(plainText, DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new SysException(SysExceptionEnum.DECRYPT_FAILED, desc);
        }
    }

}
