package com.songsmily.petapi.controller;

import com.songsmily.petapi.aspect.MyLog;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.AdminUser;
import com.songsmily.petapi.service.AdminUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/login")
@RestController
@Controller
public class AdminLoginController {
    @Resource
    AdminUserService adminUserService;

    @MyLog(value = "全局操作--登入系统")
    @RequestMapping("/adminLogin")
    public Result doLogin(AdminUser adminUser){

        return adminUserService.doLogin(adminUser);
    }
}
