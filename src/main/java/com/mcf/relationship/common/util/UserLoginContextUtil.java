package com.mcf.relationship.common.util;


import com.mcf.relationship.common.consts.NumberConst;
import com.mcf.relationship.domain.entity.UserTokenBO;

import java.util.Optional;
import java.util.function.Function;

/**
 * 用户登录上下文
 */
public final class UserLoginContextUtil {

    private static final ThreadLocal<UserTokenBO> USER_TOKEN_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    public static void clear() {
        USER_TOKEN_CONTEXT_THREAD_LOCAL.remove();
    }

    public static void setUserToken(UserTokenBO userToken) {
        USER_TOKEN_CONTEXT_THREAD_LOCAL.set(userToken);
    }

    public static UserTokenBO getUserToken() {
        return USER_TOKEN_CONTEXT_THREAD_LOCAL.get();
    }

    public static long getOrDefaultId(){
        Long userId = getUserId();
        return userId == null ? NumberConst.ZERO : userId;
    }

    public static Long getUserId() {
        return getUserTokenField(UserTokenBO::getUserId);
    }

    private static <U> U getUserTokenField(Function<? super UserTokenBO, U> mapper) {
        return Optional.ofNullable(getUserToken()).map(mapper).orElse(null);
    }

}
