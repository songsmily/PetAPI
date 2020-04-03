package com.songsmily.petapi.advice;

import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.exception.BaseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 定义统一异常处理
 *
 */
@ControllerAdvice
public class BaseExceptionAdvice {

    /**
     * 统一处理 BaseException
     *
     * @param exception
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public Result<Object> exceptionHandler(BaseException exception) {
        return new Result<>(exception.getErrorCode(), exception.getMessage());
    }
}
