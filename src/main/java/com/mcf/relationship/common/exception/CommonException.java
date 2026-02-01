package com.mcf.relationship.common.exception;

import com.mcf.relationship.common.enums.ExceptionInterface;
import lombok.Getter;

import java.text.MessageFormat;

@Getter
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 114320386259742701L;

    /**
     * 异常码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String msg;

    /**
     * 传入的参数，主要用于格式化msg，不需要被序列化
     */
    private transient Object[] args;

    public CommonException() {
        super();
    }

    public CommonException(ExceptionInterface result, Object... args) {
        super(format(result, args));
        this.code = result.getCode();
        this.msg = format(result, args);
        this.args = args;
    }

    public static String format(ExceptionInterface result, Object... args) {
        return args.length == 0 ? result.getMsg() : MessageFormat.format(result.getMsg(), args);
    }

    public static Boolean isBizException(Throwable th) {
        return th instanceof BizException;
    }


}
