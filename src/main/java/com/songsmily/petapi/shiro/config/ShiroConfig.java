package com.songsmily.petapi.shiro.config;

import com.songsmily.petapi.shiro.common.UserModularRealmAuthenticator;
import com.songsmily.petapi.shiro.common.UserModularRealmAuthorizer;
import com.songsmily.petapi.shiro.realm.APIUserRealm;
import com.songsmily.petapi.session.CustomSessionManager;
import com.songsmily.petapi.shiro.realm.AdminUserRealm;
import com.songsmily.petapi.shiro.realm.REGUserRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {
//    @Bean
//    public APIUserRealm getRealm(){
//        return new APIUserRealm();
//    }
//    @Bean
//    public AdminUserRealm getRealm(){
//        return new AdminUserRealm();
//    }
//    @Bean
//    public REGUserRealm getRealm(){
//        return new REGUserRealm();
//    }

    @Bean
    public SecurityManager getSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setAuthenticator(modularRealmAuthenticator());
        List<Realm> realms = new ArrayList<>();
        //添加多个Realm
        realms.add(adminUserRealm());
        realms.add(apiUserRealm());
        realms.add(regUserRealm());
        securityManager.setAuthorizer(modularRealmAuthorizer());
        securityManager.setRealms(realms);
        // 自定义缓存实现 使用redis
//        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
//        securityManager.setSessionManager(sessionManager());
        //注入记住我管理器;
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }
    /**
     * 系统自带的Realm管理，主要针对多realm
     * */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        //自己重写的ModularRealmAuthenticator
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }
    /**
     * 系统自带的Realm管理，主要针对多realm 授权
     */
    @Bean
    public ModularRealmAuthorizer modularRealmAuthorizer() {
        //自己重写的ModularRealmAuthorizer
        UserModularRealmAuthorizer modularRealmAuthorizer = new UserModularRealmAuthorizer();
        return modularRealmAuthorizer;
    }

    /**
     * cookie对象
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        // 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置cookie的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(86400);
        return cookie;
    }

    /**
     * cookie管理对象
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        //Cookie 数据存在客户端的浏览器
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
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
        filterMap.put("/music/**","anon");

        filterMap.put("/user/**","user");//当前请求地址必须认证之后可以访问
        filterMap.put("/admin/**","user");//当前请求地址必须认证之后可以访问

        filterMap.put("/plot/**","user");//当前请求地址必须认证之后可以访问
//        filterMap.put("/admin/**","user");//当前请求地址必须认证之后可以访问

        filterFactory.setFilterChainDefinitionMap(filterMap);

        return filterFactory;
    }

    @Bean
    public APIUserRealm apiUserRealm(){
        return new APIUserRealm();
    }

    @Bean
    public REGUserRealm regUserRealm(){
        return new REGUserRealm();
    }
    @Bean
    public AdminUserRealm adminUserRealm(){
        return new AdminUserRealm();
    }


    /**
     * 1.redis的控制器，操作redis
     */
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);

        return redisManager;
    }

    /**
     * 2.sessionDao
     */
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }

    /**
     * 3.会话管理器
     */
    public DefaultWebSessionManager sessionManager() {
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * 4.缓存管理器
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
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
