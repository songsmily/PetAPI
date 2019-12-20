package com.songsmily.petapi.utils;

import com.songsmily.petapi.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

public class ShiroUtil {
    public static Subject getSubjct(){
        return SecurityUtils.getSubject();
    }

    public static User getUser(){
        return (User) getSubjct().getPrincipal();
    }

    /**
     * 切换身份，登录后，动态更改subject的用户属性
     * @param user
     */
    public static void setUser(User user) {
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection =
                new SimplePrincipalCollection(user, realmName);
        subject.runAs(newPrincipalCollection);
    }
}
