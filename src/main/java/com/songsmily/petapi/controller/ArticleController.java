package com.songsmily.petapi.controller;

import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Article;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.ArticleService;
import com.songsmily.petapi.utils.*;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    RedisUtils redisUtils;
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    IdWorker idWorker;

    @Resource
    ArticleService articleService;

    @RequestMapping("/list")
    public Result listArticle(@RequestBody PageQuery pageQuery){
        System.err.println(pageQuery);
        PageInfo<Article> articles = articleService.list(pageQuery);
        return new Result(articles);
    }

    @RequestMapping("/likeArticle")
    public Result likeArticle(Long articleId){

        boolean result = articleService.likeArticle(articleId);
        return new Result(ResultEnum.SUCCESS);

    }

    @RequestMapping("/delete")
    public Result deleteArticle(String id){
        System.err.println(id);
        boolean hasKey = redisUtils.hasKey("article:" + id);
        System.err.println(hasKey);
        if(hasKey){
            SessionCallback<Void> sessionCallback = new SessionCallback<Void>() {
                @Override
                public <K, V> Void execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                    redisOperations.opsForZSet().remove((K)"article:pub:ids", id);
                    redisOperations.opsForZSet().remove((K)"article:ids", id);
                    redisOperations.delete((K) ("article:content:" + id));
                    redisOperations.opsForHash().put( (K)("article:" + id), "status", 2);
                    System.out.println("删除成功");
                    return null;
                }
            };
            redisUtils.executePipelined(sessionCallback);
        }
        return new Result(ResultEnum.SUCCESS);
    }

    @RequestMapping("/add")
    public Result addArticle(@RequestBody Article article){
        Result<Article> response = articleService.create(article);
        return new Result(ResultEnum.SUCCESS);
    }


    @RequestMapping("/set")
    public Result set() {
        User user = ShiroUtil.getUser(new User());
        redisUtils.set("user", user);
        redisTemplate.opsForHash().put(String.valueOf(idWorker.nextId()), "1234", "1");
        return new Result(ResultEnum.SUCCESS);
    }

    @RequestMapping("get")
    public Result get() {
        User user = (User) redisUtils.get("user");
        return new Result(user);
    }
}
