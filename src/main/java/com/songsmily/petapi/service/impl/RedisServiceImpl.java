package com.songsmily.petapi.service.impl;

import com.songsmily.petapi.dto.BlogSelectParams;
import com.songsmily.petapi.entity.BlCollect;
import com.songsmily.petapi.entity.BlTag;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.RedisService;
import com.songsmily.petapi.utils.RedisUtils;
import com.songsmily.petapi.utils.ShiroUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
        StringBuilder key = new StringBuilder();
        key.append("blog:Read:");
        key.append(blogId);

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
        return execute;
    }

    /**
     * 查询帖子浏览总数
     * @param blogId 帖子ID
     * @return
     */
    @Override
    public Integer getBlogReadCount(String blogId){
        StringBuilder key = new StringBuilder();
        key.append("blog:Read:");
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
     * 收藏帖子
     * @param collect
     * @return
     */
    @Override
    public boolean addBlogConnect(BlCollect collect) {
        StringBuilder key = new StringBuilder();
        key.append("blog:Collect:");
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
            redisTemplate.opsForValue().set("blog:Collect:Content:" + collect.getBlogId() + ":" + collect.getUserId(), collect.getCollectionTime());
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
        key.append("blog:Collect:");
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

            redisTemplate.delete("blog:Collect:Content:" + collect.getBlogId() + ":" + userId );
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
        key.append("blog:Collect:");
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
        key.append("blog:Collect:");
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
     * 获取用户是否点赞
     * @param blogId
     * @return
     */
    @Override
    public boolean getIsCommentGood(String blogId) {
        StringBuilder key = new StringBuilder();
        key.append("comment:Good:");
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
     * 帖子点赞
     * @param blogId 被点赞帖子
     * @return
     */
    @Override
    public boolean addBlogGood(String blogId) {
        StringBuilder key = new StringBuilder();
        key.append("blog:Good:");
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
        key.append("blog:Good:");
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
     * 取消评论点赞
     * @param commentId
     * @return
     */
    @Override
    public boolean removeCommentGood(String commentId) {
        StringBuilder key = new StringBuilder();
        key.append("comment:Good:");
        key.append(commentId);

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
     * 评论点赞
     * @param commentId
     * @return
     */
    @Override
    public boolean addCommentGood(String commentId) {
        StringBuilder key = new StringBuilder();
        key.append("comment:Good:");
        key.append(commentId);

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
     * 获取评论点赞数
     * @param commentId
     * @return
     */
    @Override
    public Integer getCommentGoodCount(String commentId) {
        StringBuilder key = new StringBuilder();
        key.append("comment:Good:");
        key.append(commentId);

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
     * 获取所有帖子标签
     * @return 封装成实体返回
     */
    @Override
    public List<BlTag> getBlogTags() {
        Map<String, String> entries = redisTemplate.opsForHash().entries("blog:Tags");
//        System.err.println(entries.containsKey("10"));
        List<BlTag> tags = new ArrayList<>();
        BlTag tag = new BlTag();
        for (String key: entries.keySet()){
            tag = new BlTag();
            tag.setTagId(Integer.valueOf(key));
            tag.setTagName(entries.get(key));
            tags.add(tag);
        }
        return tags;
    }

    /**
     * 添加/设置帖子标签   redis没有数据的情况下调用此方法将数据库中的数据存入  或者新增标签
     * @param blTags
     */
    @Override
    public void setBlogTags(List<BlTag> blTags) {
        Map<String,String > tags = new HashMap<>();

        for (BlTag tag : blTags) {
            tags.put(String.valueOf(tag.getTagId()), tag.getTagName());
        }
        redisTemplate.opsForHash().putAll("blog:Tags",  tags);
    }

    /**
     * 清空帖子标签
     */
    @Override
    public void clearBlogTag() {
        List<BlTag> tags = getBlogTags();
        System.err.println(tags);
        for (BlTag tag: tags) {
            redisTemplate.opsForHash().delete("blog:Tags", String.valueOf(tag.getTagId()));
        }
    }

    /**
     * 用户上传帖子时 将帖子所属标签新增到redis中
     * @param tagArr
     * @return
     */
    @Override
    public boolean addBlogTags(String[] tagArr) {

        for (int i = 0; i < tagArr.length; i++) {
            redisTemplate.opsForValue().increment("blog:TagCount:" + tagArr[i], 1);
        }
        return true;

    }

    /**
     * 查询帖子标签ID所属的Tag实体
     * @param tagIdArr
     * @return
     */
    @Override
    public List<BlTag> getBlogTagsByTagIds(String[] tagIdArr) {
        List<BlTag> tags = new ArrayList<>();
        BlTag tag;
        for (int i = 0; i < tagIdArr.length ; i++) {
            tag = new BlTag();
            tag.setTagName( (String)redisTemplate.opsForHash().get("blog:Tags", tagIdArr[i]));
            tag.setTagId(Integer.valueOf(tagIdArr[i]));
            tags.add(tag);
        }
        return tags;
    }

    /**
     * 获取热门标签
     * @return
     */
    @Override
    public List<BlTag> getHotTags() {
        List<BlTag> hotTag = new ArrayList<>();

        List<BlTag> tags = getBlogTags();
        for (BlTag tag : tags) {
            Integer res = (Integer) redisTemplate.opsForValue().get("blog:TagCount:" + tag.getTagId());
            if (null != res && 0 != res){
                tag.setTagCount(res);
                hotTag.add(tag);
            }

        }
        return hotTag;
    }

    /**
     * 查询用户收藏的帖子ID
     * @return
     * @param params
     */
    @Override
    public List<BlCollect> getUserCollectBlogIds(BlogSelectParams params) {
        Set keys = redisTemplate.keys("blog:Collect:Content:*:" + ShiroUtil.getUser(new User()).getId());
        int from = (params.getCurrentPage() - 1) * params.getPageSize();
        int to = (params.getCurrentPage() - 1) * params.getPageSize() + params.getPageSize();
        if (from > keys.size()){
            from = keys.size() - 1;
        }
        if (to > keys.size()){
            to = keys.size();
        }
        Object[] objects = keys.toArray();
        objects = Arrays.copyOfRange(objects, from, to);
        List<BlCollect> collects = new ArrayList<>();
        BlCollect collect;
        for (Object key: objects) {
            if (null == key)
                break;
            String keyStr = (String) key;
            collect = new BlCollect();
            collect.setBlogId(keyStr.substring(keyStr.lastIndexOf("Content:") + 8, ((String) key).lastIndexOf(":")));
            collect.setCollectionTime((Long) redisTemplate.opsForValue().get(keyStr));
            collects.add(collect);
        }
        return collects;
    }

    /**
     * 查询用户 收藏的帖子总数
     * @return
     */
    @Override
    public Integer getUserCollectBlogTotal() {
        Set keys = redisTemplate.keys("blog:Collect:Content:*:" + ShiroUtil.getUser(new User()).getId());
        return keys.size();
    }

    /**
     * 用户删除帖子   删除redis中所有关于帖子的数据
     * @param blogId
     * @return
     */
    @Override
    public boolean removeBlogALl(String blogId) {

        Set keys = redisTemplate.keys("*" + blogId+ "*");
        Long delete = redisTemplate.delete(keys);

        return delete == keys.size();
    }

    /**
     * 删除帖子评论的点赞信息
     * @param commentIds
     * @return
     */
    @Override
    public boolean removeCommentAll(List<String> commentIds) {
        for (String id : commentIds) {
            Set keys = redisTemplate.keys("*" + id+ "*");
            redisTemplate.delete(keys);
        }

        return true;
    }

    /**
     * 用户删除帖子时 将帖子所属标签从redis中做减一操作
     * @param tagArr
     * @return
     */
    @Override
    public boolean removeBlogTags(String[] tagArr) {

        for (int i = 0; i < tagArr.length; i++) {
            redisTemplate.opsForValue().decrement("blog:TagCount:" + tagArr[i], 1);
        }
        return true;

    }

    /**
     * 获取帖子点赞数
     * @param blogId 帖子ID
     * @return
     */
    @Override
    public Integer getBlogGoodCount(String blogId) {
        StringBuilder key = new StringBuilder();
        key.append("blog:Good:");
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
        key.append("blog:Good:");
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
