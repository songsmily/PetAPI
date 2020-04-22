package com.songsmily.petapi.service.impl;

import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.RedisKey;
import com.songsmily.petapi.service.GoodService;
import com.songsmily.petapi.service.RedisService;
import com.songsmily.petapi.utils.RedisUtils;
import com.songsmily.petapi.utils.ShiroUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Set;

/**
 * 点赞实现类
 */
@Service("GoodService")
public class GoodServiceImpl implements GoodService {
    @Resource
    RedisUtils redisUtils;
    @Resource
    RedisService redisService;

    @Override
    public boolean addBlogGood(String blogId) {

        boolean goodRes =  redisService.addBlogGood(blogId);

        return goodRes;
    }

    @Override
    public boolean removeBlogGood(String blogId) {
        boolean b = redisService.removeBlogGood(blogId);
        return b;
    }

    @Override
    public boolean removeCommentGood(String commentId) {
        return redisService.removeCommentGood(commentId);
    }

    @Override
    public boolean addCommentGood(String commentId) {
        return redisService.addCommentGood(commentId);
    }
}
