package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.songsmily.petapi.dto.AccessToken;
import com.songsmily.petapi.dto.GithubUser;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.provider.GithubProvider;
import com.songsmily.petapi.service.AuthService;
import com.songsmily.petapi.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service("AuthService")
public class AuthServiceImpl implements AuthService {
    @Autowired
    GithubProvider githubProvider;

    @Autowired
    UserService userService;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Override
    public User GithubAuth(String code, String state) {
         /*
        调用githubAPI实现登录
         */
        AccessToken accessTokenDTO = new AccessToken();
        accessTokenDTO.setClient_id(clientId.toString());
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser !=null){
            QueryWrapper<User> queryWrapper = Wrappers.query();
            queryWrapper.eq("account_id",githubUser.getId());
            User user = userService.getOne(queryWrapper);
            if (null == user){
                user = new User();
                String token = String.valueOf(UUID.randomUUID());
                user.setToken(token);
                user.setName(githubUser.getLogin());
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUrl(String.valueOf(githubUser.getAvatarUrl()));
                user.setBio(String.valueOf(githubUser.getBio()));
                user.setAccountType(1);
                user.setPassword(new Md5Hash(githubUser.getId().toString(),null,3).toString());
                userService.save(user);
            }
            return user;
        }else{
            //登录失败
            return null;
        }
    }
}
