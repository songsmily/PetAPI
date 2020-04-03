package com.songsmily.petapi.exception;

import com.songsmily.petapi.enums.ResultEnum;

/**
 * 自定义异常
 * @author Songsmily
 * @date
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 2450214686001409867L;

    private Integer errorCode = ResultEnum.ERROR.getCode();

    public BaseException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.errorCode = resultEnum.getCode();
    }

    public BaseException(ResultEnum resultEnum, Throwable throwable) {
        super(resultEnum.getMsg(), throwable);
        this.errorCode = resultEnum.getCode();
    }

    public BaseException(Integer errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }

    public BaseException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
