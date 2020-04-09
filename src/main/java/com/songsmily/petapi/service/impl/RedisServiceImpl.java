package com.songsmily.petapi.service.impl;

import com.songsmily.petapi.entity.BlCollect;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.RedisService;
import com.songsmily.petapi.utils.BeanUtils;
import com.songsmily.petapi.utils.RedisUtils;
import com.songsmily.petapi.utils.ShiroUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
     * 收藏帖子
     * @param collect
     * @return
     */
    @Override
    public boolean addBlogConnect(BlCollect collect) {
        StringBuilder key = new StringBuilder();
        key.append("blogConnect");
        key.append(collect.getBlogId());

        int userId = ShiroUtil.getUser(new User()).getId();
        Boolean execute = true;
        try{
            execute = (Boolean) redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    Boolean aBoolean = redisConnection.setBit(key.toString().getBytes(), userId, true);
                    redisConnection.close();
                    return true;
                }
            });
        } catch (Exception e){
            execute = false;
        }
        if (execute) {
            redisTemplate.opsForHash().putAll("blog:collect" + collect.getBlogId(), BeanUtils.beanToMap(collect));
        }
        return execute;
    }

    /**
     * 取消收藏帖子
     * @param collect
     * @return
     */
    @Override
    public boolean removeBlogConnect(BlCollect collect) {
        StringBuilder key = new StringBuilder();
        key.append("blogConnect");
        key.append(collect.getBlogId());

        int userId = ShiroUtil.getUser(new User()).getId();
        Boolean execute = true;
        try{
            execute = (Boolean) redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    Boolean aBoolean = redisConnection.setBit(key.toString().getBytes(), userId, false);
                    redisConnection.close();
                    return aBoolean;
                }
            });
        } catch (Exception e){
            execute = false;
        }
        if (execute) {

            redisTemplate.delete("blog:collect" + collect.getBlogId());
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
        StringBuilder key = new StringBuilder();
        key.append("blogConnect");
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
     * 根据帖子ID查询帖子收藏总数
     * @param blogId
     * @return
     */
    @Override
    public Integer getBlogConnectCount(String blogId) {
        StringBuilder key = new StringBuilder();
        key.append("blogConnect");
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

}
