package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.BlBlogDao;
import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.entity.BlBlog;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.BlBlogService;
import com.songsmily.petapi.service.RedisService;
import com.songsmily.petapi.utils.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * 博客表(BlBlog)表服务实现类
 *
 * @author SongSmily
 * @since 2020-04-06 11:01:31
 */
@Service("blBlogService")
public class BlBlogServiceImpl extends ServiceImpl<BlBlogDao, BlBlog> implements BlBlogService {
    @Resource
    OssUtil ossUtil;
    @Resource
    BlBlogDao blBlogDao;
    @Resource
    IdWorker idWorker;
    @Resource
    RedisUtils redisUtils;
    @Resource
    RedisService redisService;
    /**
     * 上传博客图片
     * @param image 图片文件
     * @return 返回图片路径 上传失败返回null
     */
    @Override
    public String postBlogImage(MultipartFile image) {
        String imageUrl = ossUtil.uploadImg2Oss(image);
        return imageUrl;
    }

    @Override
    public boolean deleteBlogImage(String imageUrl) {
        List<String> list = new ArrayList<>();
        list.add(imageUrl.substring(imageUrl.lastIndexOf("images/")));
        ossUtil.deleteFile20SS(list);
        return true;
    }

    @Override
    public boolean addBlog(BlBlog blBlog) {
        blBlog.setBlogId(UUID.randomUUID().toString());
        blBlog.setUserId(ShiroUtil.getUser(new User()).getId());
        blBlog.setCreatedTime(System.currentTimeMillis());
        blBlog.setUpdateTime(blBlog.getCreatedTime());

        int insert = blBlogDao.insert(blBlog);

        if (insert < 1){
            throw new BaseException(ResultEnum.ERROR);
        }


        return true;
    }

    @Override
    public BasePage<List> getBlogInType(Integer pageSize, Integer currentPage, Integer activeType) {
        Map<String, Object> selectMapper = new HashMap<>();
        int begin = (currentPage - 1) * pageSize;
        int end = pageSize;
        selectMapper.put("begin",begin);
        selectMapper.put("end",end);
        selectMapper.put("blogType",activeType);
        selectMapper.put("deleted",0);

        List<BlBlog> blogList = blBlogDao.selectBlogPageByType(selectMapper);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("blog_type", activeType);
        wrapper.eq("deleted", 0);
        int total = blBlogDao.selectCount(wrapper);

//        redisUtils.sGet("blog:good:" + blogId);
        List<Map> newRecords = new ArrayList();
        for (BlBlog blog:
                blogList) {
            //迭代从redis中获取点赞数
            Integer blogGoodCount = redisService.getBlogGoodCount(blog.getBlogId());
            blog.setBlogGoods(blogGoodCount);
            //迭代从redis中获取收藏数
            Integer blogConnectCount = redisService.getBlogConnectCount(blog.getBlogId());
            blogConnectCount =  null == blogConnectCount ? 0 : blogConnectCount;
            blog.setBlogCollect(blogConnectCount);


            Map<String, Object> objectMap = BeanUtils.beanToMap(blog);
            objectMap.put("blogTag", blog.getBlogTag().split("&"));
            newRecords.add(objectMap);
        }

        BasePage<List> basePage = new BasePage<>();
        basePage.setRecords(newRecords);
        basePage.setTotal(total);
        return basePage;
    }

    @Override
    public Map<String, Object> getBlogInfoById(String blogId) {
        BlBlog blog = blBlogDao.selectBlogById(blogId);
        if (null == blog){
            throw new BaseException(ResultEnum.ERROR);
        }

        //阅读数+1
        redisService.increBlogRead(blogId);

        //获取点赞数
        Integer blogGoodCount = redisService.getBlogGoodCount(blog.getBlogId());
        blog.setBlogGoods(blogGoodCount);
        //获取收藏数
        Integer blogConnectCount = redisService.getBlogConnectCount(blog.getBlogId());

        blogConnectCount =  null == blogConnectCount ? 0 : blogConnectCount;
        blog.setBlogCollect(blogConnectCount);
        blog.setBlogCollect(blogConnectCount);

        //查询用户是否点赞
        boolean isBlogGood = redisService.getIsBlogGood(blogId);

        //查询用户是否收藏
        boolean isBlogConnect = redisService.getIsBlogConnect(blogId);

        Map<String, Object> map = BeanUtils.beanToMap(blog);
        map.put("isBlogGood",isBlogGood);
        map.put("isBlogConnect",isBlogConnect);
        map.put("blogTag", blog.getBlogTag().split("&"));
        return map;
    }


}