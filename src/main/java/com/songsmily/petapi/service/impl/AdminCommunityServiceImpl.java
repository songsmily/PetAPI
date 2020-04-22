package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.songsmily.petapi.dao.BlBlogDao;
import com.songsmily.petapi.dao.BlCommentDao;
import com.songsmily.petapi.dao.BlCommentSecDao;
import com.songsmily.petapi.entity.BlComment;
import com.songsmily.petapi.service.AdminCommunityService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 社区管理员 状态总览
 */
@Service("AdminCommunityService")
public class AdminCommunityServiceImpl implements AdminCommunityService {

    @Resource
    BlBlogDao blogDao;

    @Resource
    BlCommentDao commentDao;

    @Resource
    BlCommentSecDao commentSecDao;

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> getOverView() {
        Map<String, Object> map = new HashMap<>();
        //得到分享总数
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("deleted",0);
        wrapper.eq("blog_type",1);
        int shareCount = blogDao.selectCount(wrapper);

        wrapper = new QueryWrapper();
        wrapper.eq("deleted",0);
        wrapper.eq("blog_type",2);
        //提问总数
        int questionCount = blogDao.selectCount(wrapper);

        wrapper  = new QueryWrapper();
        wrapper.eq("deleted",0);

        //得到一级评论总数
        int commentCount = commentDao.selectCount(wrapper);

        //得到二级评论总数
        int commentSecCount = commentDao.selectCount(wrapper);

        //查询Redis中已被筛选的帖子数
        Integer reviewBlogCount = (Integer) redisTemplate.opsForValue().get("blog:Content:Review:Blog");
        //查询Redis中已被筛选的评论数
        Integer reviewCommentCount = (Integer) redisTemplate.opsForValue().get("blog:Content:Review:Comment");

        map.put("shareCount", shareCount);
        map.put("questionCount", questionCount);
        map.put("commentCount", commentCount);
        map.put("commentSecCount", commentSecCount);
        map.put("reviewBlogCount", reviewBlogCount);
        map.put("reviewCommentCount", reviewCommentCount);

        return map;
    }
}
