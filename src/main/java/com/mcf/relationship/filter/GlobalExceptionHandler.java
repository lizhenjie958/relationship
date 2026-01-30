package com.mcf.relationship.filter;

import com.mcf.relationship.base.McfResult;
import com.mcf.relationship.exception.BizException;
import com.mcf.relationship.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<Object> handleBaseException(BizException ex, WebRequest request) {
        log.error("commonException",ex);
        return new ResponseEntity<>(McfResult.fail(ex), HttpStatus.OK);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        log.error("unknownException",ex);
        return new ResponseEntity<>(McfResult.error(), HttpStatus.OK);
    }
}
