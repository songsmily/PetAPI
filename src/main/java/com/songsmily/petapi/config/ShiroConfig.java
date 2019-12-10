package com.songsmily.petapi.config;

import com.songsmily.petapi.realm.GitUserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public GitUserRealm getRealm(){
        return new GitUserRealm();
    }

    @Bean
    public SecurityManager getSecurityManager(GitUserRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
        return  securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        //1.创建过滤器工厂
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        //2.设置安全管理器
        filterFactory.setSecurityManager(securityManager);
        //3.通用配置（跳转登录页面，为授权跳转的页面）
        filterFactory.setLoginUrl("/AuthError/UnLoginError");//跳转url地址
        filterFactory.setUnauthorizedUrl("/AuthError/UnAuthError");//未授权的url
        //4.设置过滤器集合

        /**
         * 设置所有的过滤器：有顺序map
         *     key = 拦截的url地址
         *     value = 过滤器类型
         *
         */
        Map<String,String> filterMap = new LinkedHashMap<>();
//        //filterMap.put("/user/home","anon");//当前请求地址可以匿名访问
//
//        //具有某中权限才能访问
//        //使用过滤器的形式配置请求地址的依赖权限
//        //filterMap.put("/user/home","perms[user-home]"); //不具备指定的权限，跳转到setUnauthorizedUrl地址
//
//        //使用过滤器的形式配置请求地址的依赖角色
//        filterMap.put("/music/selectAll","roles[users]");
//
        filterMap.put("/music/**","authc");//当前请求地址必须认证之后可以访问

        filterFactory.setFilterChainDefinitionMap(filterMap);

        return filterFactory;
    }

    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions)
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
