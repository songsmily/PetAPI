package com.songsmily.petapi.controller;

import com.alibaba.fastjson.JSON;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.provider.GithubProvider;
import com.songsmily.petapi.service.AuthService;
import com.songsmily.petapi.service.UserService;
import okhttp3.*;
import okhttp3.RequestBody;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
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
    @Autowired
    GithubProvider githubProvider;

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Value("${project.front.uri}")
    private String frontUri;

    @GetMapping("/QQAuthCallBack")
    public String QQAuthCallBack(@RequestParam(name = "code") String code,
                                 @RequestParam(name = "state") String state){
        User user = authService.QQAuth(code, state);
        //构造登录令牌
        try {

            /**
             * 密码加密：
             *     shiro提供的md5加密
             *     Md5Hash:
             *      参数一：加密的内容
             *              111111   --- abcd
             *      参数二：盐（加密的混淆字符串）（用户登录的用户名）
             *              111111+混淆字符串
             *      参数三：加密次数
             *
             */

            UsernamePasswordToken upToken = new UsernamePasswordToken(user.getName(), user.getPassword());
            //1.获取subject
            Subject subject = SecurityUtils.getSubject();
//            //获取session
//            String sid = (String) subject.getSession().getId();

            //2.调用subject进行登录
            subject.login(upToken);
            return "redirect:" + frontUri;
        } catch (Exception e) {
            return "redirect:" + frontUri + "/error";
        }
    }

    @GetMapping("/QQCallBack")
    public String QQCallback(@RequestParam(name = "code") String code,
                             @RequestParam(name = "state") String state) {
        String url = "http://localhost:8099/QQAuthCallBack?code=" + code+ "&state=" + state;
        return "redirect:" + url;
//        MediaType mediaType
//                = MediaType.get("application/json; charset=utf-8");
//
//        OkHttpClient client = new OkHttpClient();
//        String url = "https://localhost:8099/QQAuthCallBack?code=" + code+ "&client_secret=" + state;
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    @GetMapping("/GithubCallback")
    public String GithubCallback(@RequestParam(name = "code") String code,
                                 @RequestParam(name = "state") String state,
                                 HttpServletRequest request, HttpServletResponse response) {
        /*
        调用githubAPI实现登录
         */

//        User user = authService.GithubAuth(code, state);
        //构造登录令牌
        try {

            /**
             * 密码加密：
             *     shiro提供的md5加密
             *     Md5Hash:
             *      参数一：加密的内容
             *              111111   --- abcd
             *      参数二：盐（加密的混淆字符串）（用户登录的用户名）
             *              111111+混淆字符串
             *      参数三：加密次数
             *
             */

            UsernamePasswordToken upToken = new UsernamePasswordToken("songsmily", "671176cc612a8760f317c927528d340f");
            //1.获取subject
            Subject subject = SecurityUtils.getSubject();
//
//            //获取session
//            String sid = (String) subject.getSession().getId();

            //2.调用subject进行登录
            subject.login(upToken);
            return "redirect:" + frontUri;
        } catch (Exception e) {
            return "redirect:" + frontUri + "/error";
        }

    }
}
