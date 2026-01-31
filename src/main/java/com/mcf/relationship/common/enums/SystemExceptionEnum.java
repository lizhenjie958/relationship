package com.mcf.relationship.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author ZhuPo
 * @date 2026/1/24 19:46
 */
@AllArgsConstructor
@Getter
public enum SystemExceptionEnum implements ExceptionInterface {
    SYSTEM_ERROR(9000, "系统异常"),
    SING_ERROR(9001,"签名错误"),
    ENCRYPT_FAILED(9002, "{0}加密失败"),
    DECRYPT_FAILED(9003, "{0}解密失败"),
    PARAM_EMPTY(9004,"{0}参数为空"),
    DATA_EXIST(9005, "{0}数据已存在"),
    DATA_NOT_EXIST(9006, "{0}数据不存在"),
    PARAM_ILLEGAL(9007, "{0}参数不合法"),
    PARAM_GREATER_THAN_MAX_VALUE(9008, "{0}大于最大值,最大值为:{1}"),
    PARAM_LESS_THAN_MIX_VALUE(9009, "{0}小于最小值,最小值为:{1}"),
    ;
    private final Integer code;
    private final String msg;
}
