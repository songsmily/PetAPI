package com.songsmily.petapi.shiro.common;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserToken extends UsernamePasswordToken {
    //登录类型，判断是API用户生登录还是注册用户登录
    private String loginType;

    public UserToken(final String username, final String password,String loginType) {
        super(username,password,true);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
