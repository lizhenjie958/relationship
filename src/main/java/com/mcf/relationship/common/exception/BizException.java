package com.mcf.relationship.common.exception;

import com.mcf.relationship.common.enums.BizExceptionEnum;

import java.io.Serial;

/**
 * 业务异常类
 *
 * @Author caoyuzheng
 * @Date 2023/7/19 11:38
 * @Version V1.0
 */
public class BizException extends CommonException {


    @Serial
    private static final long serialVersionUID = -8313868755856058923L;

    public BizException(BizExceptionEnum result, Object... args) {
        super(result, args);
    }

    public static Boolean isBizException(Throwable th) {
        return th instanceof BizException;
    }

}
