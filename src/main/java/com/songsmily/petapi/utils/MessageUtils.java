package com.songsmily.petapi.utils;


import com.alibaba.fastjson.JSONObject;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.enums.ResultEnum;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageUtils {
    @Value("${message.appUrl}")
    private String appUrl;
    @Value("${message.appId}")
    private String appId;
    @Value("${message.appSecret}")
    private String appSecret;

    public boolean sendMessage(Map<String, String> map) {
        ZhenziSmsClient client = new ZhenziSmsClient(appUrl, appId, appSecret);
        JSONObject json;
        try {
            String result = client.send(map);
            json = JSONObject.parseObject(result);
            System.err.println(json);
            if (json.getIntValue("code") != 0){
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
