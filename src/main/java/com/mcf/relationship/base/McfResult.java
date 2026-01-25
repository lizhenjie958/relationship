package com.mcf.relationship.base;

import com.mcf.relationship.exception.BizExceptionEnum;
import com.mcf.relationship.exception.ExceptionInterface;
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

    public static <T> McfResult<T> fail(ExceptionInterface exception) {
        if (exception instanceof BizExceptionEnum) {
            return new McfResult<>(exception.getCode(), exception.getMsg(), null);
        }
        return new McfResult<>(exception.getCode(), "系统异常", null);
    }
}
