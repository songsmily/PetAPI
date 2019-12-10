package com.songsmily.petapi.exception;

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
    public Map<String, Integer> unLoginError(){
        Map<String,Integer> jsonResult = new HashMap<>();
        jsonResult.put("code",500);
        System.err.println("1111");
        return jsonResult;
    }
    @RequestMapping("/UnAuthError")
    @ResponseBody
    public Map<String, Integer> unAuthError(){
        Map<String,Integer> jsonResult = new HashMap<>();
        jsonResult.put("code",600);
        return jsonResult;
    }
}
