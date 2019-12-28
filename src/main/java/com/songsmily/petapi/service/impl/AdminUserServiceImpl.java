package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.AdminUserDao;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.AdminUser;
import com.songsmily.petapi.service.AdminUserService;
import com.songsmily.petapi.shiro.common.UserToken;
import com.songsmily.petapi.shiro.enums.LoginType;
import com.songsmily.petapi.utils.CommonUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (AdminUser)表服务实现类
 *
 * @author SongSmily
 * @since 2019-12-27 18:34:54
 */
@Service("adminUserService")
public class AdminUserServiceImpl extends ServiceImpl<AdminUserDao, AdminUser> implements AdminUserService {
    private static final String ADMINUSER_LOGIN_TYPE = LoginType.ADMINUSER.toString();

    @Override
    public Result doLogin(AdminUser adminUser) {
        String username = adminUser.getUsername();
        String password = CommonUtils.setPassword(adminUser.getPassword());
        System.err.println(password);
        try {
            UserToken userToken = new UserToken(username,password, ADMINUSER_LOGIN_TYPE);
            //1.获取subject
            Subject subject = SecurityUtils.getSubject();
            //2.调用subject进行登录
            subject.login(userToken);
            return new Result(CodeMsg.SUCCESS);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(CodeMsg.SERVERERROR);
        }
    }
}