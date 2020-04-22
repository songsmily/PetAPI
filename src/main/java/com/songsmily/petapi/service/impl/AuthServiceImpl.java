package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.songsmily.petapi.dto.AccessToken;
import com.songsmily.petapi.dto.GithubUser;
import com.songsmily.petapi.dto.QQUser;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.provider.GithubProvider;
import com.songsmily.petapi.provider.QQProvider;
import com.songsmily.petapi.service.AuthService;
import com.songsmily.petapi.service.UserService;
import com.songsmily.petapi.utils.CharCodeFilt;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service("AuthService")
public class AuthServiceImpl implements AuthService {
    //注入工具类
    @Autowired
    CharCodeFilt charCodeFilt;
    //注入github授权接口
    @Autowired
    GithubProvider githubProvider;
    //注入qq授权接口
    @Autowired
    QQProvider qqProvider;
    //注入用户服务接口
    @Autowired
    UserService userService;
    //注入github clientId
    @Value("${github.client.id}")
    private String GithubclientId;
    //注入github ClientSecret
    @Value("${github.client.secret}")
    private String GithubClientSecret;
    //注入github授权回调地址
    @Value("${github.redirect.uri}")
    private String GithubRedirectUri;
    //注入qq应用id
    @Value("${qq.qqAppId}")
    private String QQAppId;
    //注入qq应用秘钥
    @Value("${qq.qqAppSecret}")
    private String QQSecret;

   //注入qq授权回调地址
    @Value("${qq.qqRedirectUrl}")
    private String QQRedirectUrl;


    /**
     * qq授权
     * @param code
     * @param state
     * @return
     */
    public User QQAuth(String code, String state){
        /**
         * 封装accesstoken并发送请求获取user信息
         */
        AccessToken accessTokenDTO = new AccessToken();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(QQAppId.toString());
        accessTokenDTO.setClient_secret(QQSecret);
        accessTokenDTO.setRedirect_uri(QQRedirectUrl);
        String accessToken = qqProvider.getAccessToken(accessTokenDTO);
        String openId = qqProvider.getOpenId(accessToken);
        QQUser qqUser =qqProvider.getUser(accessToken,openId,QQAppId);
        if (qqUser != null){
            QueryWrapper<User> queryWrapper = Wrappers.query();
            queryWrapper.eq("account_id",openId);
            User user = userService.getOne(queryWrapper);
            //判断用户是是否已存在数据库中
            if (null == user){
                user = new User();
                if (!charCodeFilt.isHasEmoji(qqUser.getNickname())){
                    qqUser.setNickname("昵称(含非法字符)");
                }
                user.setName(qqUser.getNickname());
                user.setAccountId(openId);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUrl(String.valueOf(qqUser.getFigureurl_2()));
                user.setAccountType(2);
                user.setBio("签名是一种态度");
                user.setStatus(0);
                //qq授权用户密码为openId哈希散列3次得到
                user.setPassword(new Md5Hash(openId,null,3).toString());

                userService.save(user);
            }
            return user;
        }else{
            return null;
        }
    }
    /*
   调用githubAPI实现登录
    */
    public User GithubAuth(String code, String state) {
        AccessToken accessTokenDTO = new AccessToken();
        accessTokenDTO.setClient_id(GithubclientId.toString());
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(GithubClientSecret);
        accessTokenDTO.setRedirect_uri(GithubRedirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser !=null){
            QueryWrapper<User> queryWrapper = Wrappers.query();
            queryWrapper.eq("account_id",githubUser.getId());
            User user = userService.getOne(queryWrapper);
            if (null == user){
                user = new User();
                user.setName(githubUser.getLogin());
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUrl(String.valueOf(githubUser.getAvatarUrl()));
                user.setAccountType(1);
                user.setPassword(new Md5Hash(githubUser.getId().toString(),null,3).toString());
                user.setBio("签名是一种态度");
                user.setStatus(0);
                userService.save(user);
            }
            return user;
        }else{
            //登录失败
            return null;
        }
    }
}
