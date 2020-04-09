package com.songsmily.petapi.service.impl;

import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Article;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.ArticleService;
import com.songsmily.petapi.utils.*;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service("ArticleService")
public class ArticleServiceImpl implements ArticleService {
    @Resource
    IdWorker idWorker;

    @Resource
    RedisUtils redisUtils;

    @Override
    public Result<Article> create(Article article) {
        article.setId(idWorker.nextId());
        article.setStatus(0);
        java.util.Date now = new java.util.Date();
        article.setCreated(now);
        article.setModified(now);
        article.setAllowComment(true);
        SessionCallback<Void> sessionCallback = new SessionCallback<Void>() {
            @Override
            public <K, V> Void execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                redisOperations.opsForHash().putAll((K)("article:" + article.getId()), BeanUtils.beanToMap(article, "userLike"));
                redisOperations.opsForValue().set((K)("article:content:" + article.getId()), (V)article.getContent());
                String score = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                redisOperations.opsForZSet().add((K)"article:ids", (V)article.getId(), Double.parseDouble(score));
                return null;
            }
        };
        redisUtils.executePipelined(sessionCallback);

        return new Result<>(article);
    }

    @Override
    public PageInfo<Article> list(PageQuery pageQuery) {
        int stopIndex = pageQuery.getStartIndex()+pageQuery.getPageSize()-1;
        Set<Object> ids = redisUtils.zRevRange("article:ids", pageQuery.getStartIndex(), stopIndex);
        System.err.println(ids);
        for (Object id:
             ids) {
            Map<Object,Object> result = redisUtils.hmget("article:" + id);
            System.err.println(result);
            redisUtils.hincr("article:" + id,"likeCount", 1);
            Article article = BeanUtils.mapToBean(result, Article.class);
            System.out.println(article);
        }

//        List<Article> articleList =
//                ids.stream()
//                        .map(e -> {
//                            Map<Object,Object> result = redisUtils.hmget("article:" + e);
//                            return BeanUtils.mapToBean(result, Article.class);})
//                        .filter(e->(e!=null) && e.getId()!=null && NumberUtils.zeroOnNull(e.getStatus())<=1)
//                        .sorted(Article::compareByCreated)
//                        .collect(Collectors.toList());
        return new PageInfo<>(null, pageQuery);
    }

    @Override
    public boolean likeArticle(Long articleId) {
        User user = ShiroUtil.getUser(new User());
        Map<Object, Object> hmget = redisUtils.hmget("article:" + articleId);
        Article article = new Article();
        article.setAllowComment((Boolean) hmget.get("allowComment"));
        article.setCreated((Date) hmget.get("created"));
        article.setId(idWorker.nextId());
        Map<String, Object> objectMap = BeanUtils.beanToMap(article);
        long num = redisUtils.sAdd("article:like:"+articleId, Math.random() * 10);
        redisUtils.hincr("article:" + articleId,"likeCount", 1);
        return true;
    }


}
