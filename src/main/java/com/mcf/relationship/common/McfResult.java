package com.mcf.relationship.common;

import com.mcf.relationship.enums.SystemExceptionEnum;
import com.mcf.relationship.exception.CommonException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ZhuPo
 * @date 2026/1/24 19:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class McfResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -4919850150553156286L;
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 结果数据
     */
    private T data;

    public static <T> McfResult<T> success() {
        return new McfResult<>(200, "处理成功", null);
    }

    public static <T> McfResult<T> success(T data) {
        return new McfResult<>(200, "处理成功", data);
    }

    public static <T> McfResult<T> fail(CommonException exception) {
        if (CommonException.isBizException(exception)) {
            return new McfResult<>(exception.getCode(), exception.getMsg(), null);
        }
        return new McfResult<>(exception.getCode(), "服务器繁忙，请稍后重试", null);
    }

    public static <T> McfResult<T> error() {
        return new McfResult<>(SystemExceptionEnum.SYSTEM_ERROR.getCode(), "系统异常，请联系客服人员处理", null);
    }

    public static <T> McfResult<T> signError() {
        return new McfResult<>(SystemExceptionEnum.SING_ERROR.getCode(), SystemExceptionEnum.SING_ERROR.getMsg(), null);
    }
}
