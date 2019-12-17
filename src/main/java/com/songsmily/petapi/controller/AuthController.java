package com.songsmily.petapi.controller;

import com.alibaba.fastjson.JSON;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.provider.GithubProvider;
import com.songsmily.petapi.service.AuthService;
import com.songsmily.petapi.service.UserService;
import com.songsmily.petapi.shiro.common.UserToken;
import com.songsmily.petapi.shiro.enums.LoginType;
import okhttp3.*;
import okhttp3.RequestBody;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@CrossOrigin
public class AuthController {
    //注入用户相关服务类
    @Autowired
    UserService userService;
    //注入授权服务类
    @Autowired
    AuthService authService;

    //注入跳转地址，用于三方接口登录后的页面跳转
    @Value("${project.front.uri}")
    private String frontUri;

    private static final String APIUSER_LOGIN_TYPE = LoginType.APIUSER.toString();
    private static final String REGUSER_LOGIN_TYPE = LoginType.REGUSER.toString();

    /**
     *处理QQ回调请求头错误不能实现shiro授权的错误
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/QQAuthCallBack")
    public String QQAuthCallBack(@RequestParam(name = "code") String code,
                                 @RequestParam(name = "state") String state) {
        //调用QQ授权服务接口获取user信息
        User user = authService.QQAuth(code, state);
        return APIAuth(user);
    }

    /**
     * QQ登录回调执行，获取code和state值后跳转到QQAuthCallBack进行授权
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/QQCallBack")
    public String QQCallback(@RequestParam(name = "code") String code,
                             @RequestParam(name = "state") String state) {
        String url = "http://localhost:8099/QQAuthCallBack?code=" + code + "&state=" + state;
        return "redirect:" + url;
    }

    /**
     * Github授权回调
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/GithubCallback")
    public String GithubCallback(@RequestParam(name = "code") String code,
                                 @RequestParam(name = "state") String state) {
        /*
        调用githubAPI实现登录
         */
        User user = authService.GithubAuth(code, state);
        return APIAuth(user);

    }
    private String APIAuth(User user){
        //构造登录令牌
        try {
            UserToken userToken = new UserToken(user.getAccountId(),user.getPassword(), APIUSER_LOGIN_TYPE);
//            UsernamePasswordToken upToken = new UsernamePasswordToken(user.getAccountId(), user.getPassword());
            //1.获取subject
            Subject subject = SecurityUtils.getSubject();
            //2.调用subject进行登录
            subject.login(userToken);
            return "redirect:" + frontUri;
        } catch (Exception e) {
            return "redirect:" + frontUri + "/error";
        }
    }
}
