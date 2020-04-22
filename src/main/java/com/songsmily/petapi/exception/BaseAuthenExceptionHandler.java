package com.songsmily.petapi.exception;

import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.enums.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    处理未登录请求的响应
 */
@Controller
@RequestMapping("/AuthError")
public class BaseAuthenExceptionHandler {

    @RequestMapping("/UnLoginError")
    @ResponseBody
    public Result unLoginError(){
        return new Result(ResultEnum.NOT_LOGIN);
    }
}
