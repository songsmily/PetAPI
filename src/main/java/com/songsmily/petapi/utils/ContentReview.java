package com.songsmily.petapi.utils;

import com.songsmily.petapi.config.BaiDuPApiConfig;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 内容审核
 */
@Component
public class ContentReview {

    @Resource
    RedisTemplate redisTemplate;

    /**
     * 签名审核
     * @param text
     * @return
     */
    public void reviewBio(String text) {
        try {
            JSONObject jsonObject = BaiDuPApiConfig.client.textCensorUserDefined(text);
            boolean equals = jsonObject.getString("conclusion").equals("合规");
            System.err.println(equals);
           if (!equals) {
               throw  new BaseException(ResultEnum.BIO_REVIEW_ERROR);
           }
        } catch (JSONException e) {
            throw  new BaseException(ResultEnum.ERROR);
        }
    }

    /**
     * 文本审核
     * @param text
     * @return
     */
    public void reviewText(String text) {
        try {
            JSONObject jsonObject = BaiDuPApiConfig.client.textCensorUserDefined(text);
            System.err.println("-------------");
            boolean equals = jsonObject.getString("conclusion").equals("合规");
            System.err.println(equals);
           if (!equals) {
                redisTemplate.opsForValue().increment("blog:Content:Review:Blog",1);
               throw  new BaseException(ResultEnum.CONTENT_REVIEW_ERROR);
           }
        } catch (JSONException e) {
            throw  new BaseException(ResultEnum.ERROR);
        }
    }

    public  void reviewAvartar(MultipartFile image)  {

        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(image.getInputStream());
            JSONObject jsonObject = BaiDuPApiConfig.client.imageCensorUserDefined(bytes, null);

            boolean equals = jsonObject.getString("conclusion").equals("合规");

            if (!equals) {
                throw  new BaseException(ResultEnum.AVATAR_REVIEW_ERROR);
            }
        } catch (IOException | JSONException e){

            throw  new BaseException(ResultEnum.ERROR);
        }

    }
    public  void reviewImage(MultipartFile image)  {

        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(image.getInputStream());
            JSONObject jsonObject = BaiDuPApiConfig.client.imageCensorUserDefined(bytes, null);

            boolean equals = jsonObject.getString("conclusion").equals("合规");

            if (!equals) {
                redisTemplate.opsForValue().increment("blog:Content:Review:Comment",1);
                throw  new BaseException(ResultEnum.CONTENT_REVIEW_ERROR);
            }
        } catch (IOException | JSONException e){

            throw  new BaseException(ResultEnum.ERROR);
        }

    }
}
