package com.mcf.relationship.common.exception;

import com.mcf.relationship.common.enums.SysExceptionEnum;

import java.io.Serial;

/**
 * 系统异常类
 *
 * @Author caoyuzheng
 * @Date 2023/7/19 11:38
 * @Version V1.0
 */
public class SysException extends CommonException {

    @Serial
    private static final long serialVersionUID = -5409047126189941382L;

    public SysException(SysExceptionEnum result, Object... args) {
        super(result, args);
    }

    public SysException(){
        super();
    }
}
