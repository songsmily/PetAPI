package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.BlBlogDao;
import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.dto.BlogSelectParams;
import com.songsmily.petapi.entity.BlBlog;
import com.songsmily.petapi.entity.BlCollect;
import com.songsmily.petapi.entity.BlTag;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.BlBlogService;
import com.songsmily.petapi.service.RedisService;
import com.songsmily.petapi.utils.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.jvm.hotspot.utilities.IntArray;

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

    /**
     * 删除博客图片
     * @param imageUrl
     * @return
     */
    @Override
    public boolean deleteBlogImage(String imageUrl) {
        List<String> list = new ArrayList<>();
        list.add(imageUrl.substring(imageUrl.lastIndexOf("images/")));
        ossUtil.deleteFile20SS(list);
        return true;
    }

    /**
     * 上传帖子
     * @param blBlog
     * @return
     */
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

        String[] tagArr = blBlog.getBlogTag().split("&");
        tagArr =  Arrays.copyOfRange(tagArr, 1, tagArr.length);

        boolean res = redisService.addBlogTags(tagArr);
        return true;
    }

    /**
     * 根据帖子类型 分页查询帖子
     * @param pageSize
     * @param currentPage
     * @param activeType
     * @return
     */
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
            //从redis中根据标签ID获取标签对象
            String[] tagArr = blog.getBlogTag().split("&");
            tagArr =  Arrays.copyOfRange(tagArr, 1, tagArr.length);
            objectMap.put("blogTag", redisService.getBlogTagsByTagIds(tagArr));
            newRecords.add(objectMap);
        }

        BasePage<List> basePage = new BasePage<>();
        basePage.setRecords(newRecords);
        basePage.setTotal(total);
        return basePage;
    }

    /**
     * 根据ID查询帖子
     * @param blogId
     * @return
     */
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

        String[] tagArr = blog.getBlogTag().split("&");
        tagArr =  Arrays.copyOfRange(tagArr, 1, tagArr.length);
        map.put("blogTag", redisService.getBlogTagsByTagIds(tagArr));
        return map;
    }

    /**
     * 根据帖子ID查询帖子
     * @param params
     * @return
     */
    @Override
    public BasePage<List> getBlogInfoByHotTag(BlogSelectParams params) {
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("begin", (params.getCurrentPage() - 1) * params.getPageSize());
        selectMap.put("end", params.getPageSize());
        selectMap.put("tag", params.getHotTag());
        List<BlBlog> blogList = blBlogDao.selectBlogPageByHotTag(selectMap);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("blog_tag", "&" + params.getHotTag() + "&");
        wrapper.eq("deleted", 0);
        int total = blBlogDao.selectCount(wrapper);

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
            //从redis中根据标签ID获取标签对象
            String[] tagArr = blog.getBlogTag().split("&");
            tagArr =  Arrays.copyOfRange(tagArr, 1, tagArr.length);
            objectMap.put("blogTag", redisService.getBlogTagsByTagIds(tagArr));
            newRecords.add(objectMap);
        }
        BasePage<List> basePage = new BasePage<>();

        basePage.setRecords(newRecords);
        basePage.setTotal(total);
        return basePage;
    }

    /**
     * 查询我的博客
     * @param params
     * @return
     */
    @Override
    public BasePage<List> getMyBlog(BlogSelectParams params) {
        Map<String, Object> selectMap = new HashMap<>();
        Integer userId = ShiroUtil.getUser(new User()).getId();
        selectMap.put("begin", (params.getCurrentPage() - 1) * params.getPageSize());
        selectMap.put("end", params.getPageSize());
        selectMap.put("tag", params.getHotTag());
        selectMap.put("blogType", -1);
        selectMap.put("deleted", 0);
        selectMap.put("userId", userId);
        List<BlBlog> blogList = blBlogDao.selectBlogPageByType(selectMap);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        wrapper.eq("deleted", 0);
        Integer total = blBlogDao.selectCount(wrapper);

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
            //从redis中根据标签ID获取标签对象
            String[] tagArr = blog.getBlogTag().split("&");
            tagArr =  Arrays.copyOfRange(tagArr, 1, tagArr.length);
            objectMap.put("blogTag", redisService.getBlogTagsByTagIds(tagArr));
            newRecords.add(objectMap);
        }

        BasePage<List> basePage = new BasePage<>();
        basePage.setRecords(newRecords);
        basePage.setTotal(total);
        return basePage;
    }

    /**
     * 获取我的收藏
     * @param params
     * @return
     */
    @Override
    public BasePage<List> getMyCollect(BlogSelectParams params) {
        List<BlCollect> collects = redisService.getUserCollectBlogIds(params);
        List<BlBlog> blogs = null;
        if (collects.size() > 0){
            blogs = blBlogDao.selectBlogByIds(collects);
            for (BlBlog blog: blogs) {
                for (BlCollect collect: collects) {
                    if (collect.getBlogId().equals(blog.getBlogId())) {
                        blog.setCollectTime(collect.getCollectionTime());
                        break;
                    }
                }
            }
        }

        List<Map> newRecords = new ArrayList();
        if (null != blogs && blogs.size() > 0){
            for (BlBlog blog:
                    blogs) {
                //迭代从redis中获取点赞数
                Integer blogGoodCount = redisService.getBlogGoodCount(blog.getBlogId());
                blog.setBlogGoods(blogGoodCount);
                //迭代从redis中获取收藏数
                Integer blogConnectCount = redisService.getBlogConnectCount(blog.getBlogId());
                blogConnectCount =  null == blogConnectCount ? 0 : blogConnectCount;
                blog.setBlogCollect(blogConnectCount);

                Map<String, Object> objectMap = BeanUtils.beanToMap(blog);
                //从redis中根据标签ID获取标签对象
                String[] tagArr = blog.getBlogTag().split("&");
                tagArr =  Arrays.copyOfRange(tagArr, 1, tagArr.length);
                objectMap.put("blogTag", redisService.getBlogTagsByTagIds(tagArr));
                newRecords.add(objectMap);
            }
        }

        BasePage basePage = new BasePage();
        Integer total = redisService.getUserCollectBlogTotal();

        basePage.setTotal(total);
        basePage.setRecords(newRecords);

        return basePage;
//        return blogs;
    }


}