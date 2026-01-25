package com.mcf.relationship.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author ZhuPo
 * @date 2026/1/24 19:46
 */
@AllArgsConstructor
@Getter
public enum BizExceptionEnum implements ExceptionInterface {

    ;
    private Integer code;
    private String msg;
}
