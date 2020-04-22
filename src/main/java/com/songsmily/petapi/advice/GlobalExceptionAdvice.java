package com.songsmily.petapi.advice;

import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 捕获全局异常
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result errorHandler(HttpServletRequest reqest,
                               HttpServletResponse response, Exception e)  {
//        if (! e.getMessage().equals(String.valueOf(ResultEnum.SERVER_ERROR.getCode()))){
//            return new Result(ResultEnum.ERROR);
//        }
//        System.err.println("服务器异常");
//        e.printStackTrace();
        if (e instanceof BaseException){
            return new Result(((BaseException) e).getErrorCode(), ((BaseException) e).getMessage());
        } else if (e instanceof UnauthenticatedException){
            System.err.println("未登录----------------------------------------------------------------------------------------------------------------");

            return  new Result(ResultEnum.NOT_LOGIN);
        }else if (e instanceof UnauthorizedException){
            System.err.println("未授权----------------------------------------------------------------------------------------------------------------");
            return new Result(ResultEnum.NOT_AUTH);
        } else{
            e.printStackTrace();
            System.err.println("系统错误----------------------------------------------------------------------------------------------------------------");

            return new Result(ResultEnum.SERVER_ERROR);
        }
    }

}
