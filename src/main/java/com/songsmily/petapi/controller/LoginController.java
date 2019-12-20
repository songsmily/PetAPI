package com.songsmily.petapi.controller;

import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.UserService;
import com.songsmily.petapi.shiro.common.UserToken;
import com.songsmily.petapi.shiro.enums.LoginType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/login")
@RestController
public class LoginController {
    @Autowired
    UserService userService;
    private static final String REGUSER_LOGIN_TYPE = LoginType.REGUSER.toString();

    @RequestMapping("/doLogin")
    public Result doLogin(User user){
        user.setPassword(new Md5Hash(user.getPassword(),null,3).toString());
        try {
//            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken()
            UserToken userToken = new UserToken(user.getPhone(),user.getPassword(), REGUSER_LOGIN_TYPE);
            //1.获取subject
            Subject subject = SecurityUtils.getSubject();
            //2.调用subject进行登录
            subject.login(userToken);
            return new Result(CodeMsg.SUCCESS);
        } catch (Exception e) {
           return new Result(CodeMsg.AUTHENERROR);
        }
    }
}
