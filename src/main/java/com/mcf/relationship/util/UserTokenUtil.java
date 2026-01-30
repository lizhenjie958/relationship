package com.mcf.relationship.util;

import com.alibaba.fastjson2.JSONObject;
import com.mcf.relationship.bo.UserTokenBO;

/**
 * @author lizhenjie
 * @date 7/11/23 9:04 上午
 */
public class UserTokenUtil {

    /**
     * 解密
     *
     * @param token
     * @return
     */
    public static UserTokenBO decrypt(String token) {
        String decryptStr = EncryptUtil.decrypt(token,"token");
        UserTokenBO userToken = JSONObject.parseObject(decryptStr, UserTokenBO.class);
        AssertUtil.checkObjectNotNull(userToken, "登录信息为空");
        return userToken;
    }

    /**
     * 加密
     *
     * @param userToken
     * @return
     */
    public static String encrypt(UserTokenBO userToken) {
        AssertUtil.checkObjectNotNull(userToken, "用户登录信息");
        AssertUtil.checkObjectNotNull(userToken.getLoginTime(),"登录时间");
        AssertUtil.checkObjectNotNull(userToken.getUserId(), "用户ID");
        AssertUtil.checkObjectNotNull(userToken.getOpenId(), "微信用户ID");
        String userTokenJson = JSONObject.toJSONString(userToken);
        return EncryptUtil.encrypt(userTokenJson,"token");
    }
}
