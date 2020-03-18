package com.songsmily.petapi.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.songsmily.petapi.entity.AdminUser;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.AdminUserService;
import com.songsmily.petapi.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Collection;

public class AdminUserRealm extends AuthorizingRealm {

    private Logger logger= LoggerFactory.getLogger(AdminUserRealm.class);
    @Override
    public String getName() {
        return "AdminUser";
    }

    @Autowired
    private AdminUserService adminUserService;


    /**
     * 授权方法
     *      操作的时候，判断用户是否具有响应的权限
     *          先认证 -- 安全数据
     *          再授权 -- 根据安全数据获取用户具有的所有操作权限
     *
     *
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.err.println("------------进入管理员用户授权");
        //1.获取已认证的用户数据
        AdminUser adminUser = (AdminUser) principalCollection.getPrimaryPrincipal();//得到唯一的安全数据
        //2.根据用户数据获取用户的权限信息（所有角色，所有权限）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("admin");

        info.addStringPermission("admin-all");
        if (adminUser.getAccountType() == 1){
            info.addStringPermission("pet-admin");
        }else if (adminUser.getAccountType() == 2){

        }else{
            info.addStringPermission("sys-admin");

        }

        return info;
    }


    /**
     * 认证方法
     *  参数：传递的用户名密码
     * @return
     */
    @Value("${login.timeout}")
     private int timeout;
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SecurityUtils.getSubject().getSession().setTimeout(timeout);
        System.err.println("------------进入管理员用户登录");
        //1.获取登录的用户名密码（token）
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = new String( upToken.getPassword());
        //2.根据用户名查询数据库
        QueryWrapper<AdminUser> wrapper = Wrappers.query();
        wrapper.eq("password",password);
        wrapper.eq("username",username);
        AdminUser user = adminUserService.getOne(wrapper);
        //3.判断用户是否存在或者密码是否一致

        if(user != null && user.getPassword().equals(password)) {
            //4.如果一致返回安全数据
            //构造方法：安全数据，密码，realm域名
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
            return info;
        }
        //5.不一致，返回null（抛出异常）
        return null;
    }
}
