package com.songsmily.petapi.provider;

import com.alibaba.fastjson.JSON;
import com.songsmily.petapi.dto.AccessToken;
import com.songsmily.petapi.dto.GithubUser;
import com.songsmily.petapi.dto.QQUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class QQProvider {
    public String getAccessToken(AccessToken accessToken) {
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessToken));
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + accessToken.getClient_id() + "&client_secret=" + accessToken.getClient_secret() +
                "&redirect_uri=" + accessToken.getRedirect_uri() +
                "&code=" + accessToken.getCode();
        System.err.println(url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {

        }
        return null;
    }
    public String getOpenId(String access_token){
        OkHttpClient client = new OkHttpClient();
        String url = "https://graph.qq.com/oauth2.0/me?access_token=" + access_token;

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
//            String openId = string.split("&")[0].split("=")[1];
//            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            String openId =  StringUtils.substringBetween(string, "\"openid\":\"", "\"}");
            return openId;
        } catch (IOException e) {

        }
        return null;
    }

    public QQUser getUser(String access_token, String openid, String QQAppId){
        OkHttpClient client = new OkHttpClient();
        String url = "https://graph.qq.com/user/get_user_info?access_token=" + access_token + "&oauth_consumer_key=" + QQAppId + "&openid=" + openid;
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            return JSON.parseObject(string, QQUser.class);
        } catch (IOException e) {

        }
        return null;

    }
}
