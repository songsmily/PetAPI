package com.songsmily.petapi.service.impl;

import com.songsmily.petapi.entity.BlCollect;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.RedisService;
import com.songsmily.petapi.utils.BeanUtils;
import com.songsmily.petapi.utils.RedisUtils;
import com.songsmily.petapi.utils.ShiroUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("RedisService")
public class RedisServiceImpl implements RedisService {
    @Resource
    RedisUtils redisUtils;
    @Resource
    RedisTemplate redisTemplate;

    /**
     * 阅读数加一
     * @param blogId
     * @return
     */
    @Override
    public boolean increBlogRead(String blogId) {
        int userId = ShiroUtil.getUser(new User()).getId();
        Boolean isRead = redisTemplate.opsForSet().isMember("blog:read:" + blogId, userId);
        if (!isRead){
            long num = redisTemplate.opsForSet().add("blog:read:" + blogId, userId);
            if (num > 0){
                redisTemplate.opsForValue().increment("blog:readCount:" + blogId,1);
            }
            return true;
        }
        return false;
    }



    /**
     * 查询用户是否点赞当前帖子
     * @param blogId 帖子ID
     * @return
     */
    @Override
    public boolean getIsBlogGood(String blogId) {
        StringBuilder key = new StringBuilder();
        key.append("blogGood");
        key.append(blogId);

        return (boolean) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Boolean bit = redisConnection.getBit(key.toString().getBytes(), ShiroUtil.getUser(new User()).getId());
                redisConnection.close();
                return bit;
            }
        });
    }


    /**
     * 收藏帖子
     * @param collect
     * @return
     */
    @Override
    public boolean addBlogConnect(BlCollect collect) {
        Long num = redisTemplate.opsForSet().add("blog:connect:" + collect.getBlogId(), collect.getUserId());
        if (num > 0){
            Object res = redisTemplate.opsForValue().get("blog:connectCount:" + collect.getBlogId());
            if (null == res){
                redisTemplate.opsForValue().set("blog:connectCount:" + collect.getBlogId(), 1);
            }else {
                redisTemplate.opsForValue().increment("blog:connectCount:" + collect.getBlogId(), 1);
            }
        }

        return true;
    }

    /**
     * 取消收藏帖子
     * @param collect
     * @return
     */
    @Override
    public boolean removeBlogConnect(BlCollect collect) {
        Long remove = redisTemplate.opsForSet().remove("blog:connect:" + collect.getBlogId(), collect.getUserId());
        if (remove > 0){
            redisTemplate.opsForValue().decrement("blog:connectCount:" + collect.getBlogId(), 1);
        }
        return true;
    }

    /**
     * 查询用户是否收藏帖子
     * @param blogId 帖子ID
     * @return
     */
    @Override
    public boolean getIsBlogConnect(String blogId) {
        int userId = ShiroUtil.getUser(new User()).getId();
        return redisTemplate.opsForSet().isMember("blog:connect:" + blogId, userId);
    }

    /**
     * 根据帖子ID查询帖子收藏总数
     * @param blogId
     * @return
     */
    @Override
    public Integer getBlogConnectCount(String blogId) {
        Object res = redisUtils.get("blog:connectCount:" + blogId);
        if (null != res){
            return (Integer) res;
        }
        return null;
    }

    /**
     * 帖子点赞
     * @param blogId 被点赞帖子
     * @return
     */
    @Override
    public boolean addBlogGood(String blogId) {
        StringBuilder key = new StringBuilder();
        key.append("blogGood");
        key.append(blogId);

        int userId = ShiroUtil.getUser(new User()).getId();
        boolean pos = true;
        try{
            redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    Boolean aBoolean = redisConnection.setBit(key.toString().getBytes(), userId, true);
                    return aBoolean;
                }
            });

        } catch (Exception e){
            pos = false;
        }
        return pos;
    }
    /**
     * 取消帖子点赞
     * @param blogId 被点赞帖子
     * @return
     */
    @Override
    public boolean removeBlogGood(String blogId) {
        StringBuilder key = new StringBuilder();
        key.append("blogGood");
        key.append(blogId);

        int userId = ShiroUtil.getUser(new User()).getId();
        boolean pos = true;
        try{
            redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    Boolean aBoolean = redisConnection.setBit(key.toString().getBytes(), userId, false);
                    redisConnection.close();
                    return aBoolean;
                }
            });

        } catch (Exception e){
            pos = false;
        }
        return pos;
    }

    /**
     * 获取帖子点赞数
     * @param blogId 帖子ID
     * @return
     */
    @Override
    public Integer getBlogGoodCount(String blogId) {
        StringBuilder key = new StringBuilder();
        key.append("blogGood");
        key.append(blogId);

        Long execute = (Long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Long aLong = redisConnection.bitCount(key.toString().getBytes());
                redisConnection.close();
                return aLong;
            }
        });

        return execute.intValue();
    }

}
