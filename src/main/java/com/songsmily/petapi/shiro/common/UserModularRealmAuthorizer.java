package com.songsmily.petapi.shiro.common;

import com.songsmily.petapi.shiro.enums.LoginType;
import com.songsmily.petapi.shiro.realm.APIUserRealm;
import com.songsmily.petapi.shiro.realm.AdminUserRealm;
import com.songsmily.petapi.shiro.realm.REGUserRealm;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
public class UserModularRealmAuthorizer extends ModularRealmAuthorizer {
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)){ continue;}
            //  todo 授权配置
            if (realm.getName().contains(LoginType.ADMINUSER.toString())) {// 判断realm
                if (permission.contains("admin-all") || permission.contains("pet-admin") ||permission.contains("sys-admin")  ) {// 判断是否改realm的资源
                    return ((AdminUserRealm) realm).isPermitted(principals, permission);    // 使用改realm的授权方法
                }
            }else if (realm.getName().contains(LoginType.REGUSER.toString())){
                if (permission.contains("user-all")) {
                    return ((REGUserRealm) realm).isPermitted(principals, permission);
                }
            }else{

                if (permission.contains("user-all")) {
                    return ((APIUserRealm) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }
}
