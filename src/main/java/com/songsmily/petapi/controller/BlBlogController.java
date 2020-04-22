package com.songsmily.petapi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.dto.BlogSelectParams;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.BlBlog;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.BlBlogService;
import com.songsmily.petapi.service.BlCheckService;
import com.songsmily.petapi.utils.ContentReview;
import com.songsmily.petapi.utils.OssUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 博客表(BlBlog)表控制层
 *
 * @author SongSmily
 * @since 2020-04-06 11:01:31
 */
@RestController
@RequestMapping("blBlog")
public class BlBlogController {
    /**
     * 服务对象
     */
    @Resource
    private BlBlogService blBlogService;

    @Resource
    BlCheckService blCheckService;

    /**
     * 内容审核对象
     */
    @Resource
    ContentReview contentReview;

    /**
     * 根据帖子ID删除帖子
     * @param blogId
     * @return
     */

    @RequiresPermissions("user-all")
    @RequestMapping("deleteBlog")
    public Result deleteBlog(String blogId) {
        boolean b = blCheckService.doBlogDelete(blogId);

        return new Result(b ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }

    /**
     * 通过热门标签 查询帖子
     * @param params
     * @return
     */
    @RequiresPermissions("user-all")
    @RequestMapping("getBlogInfoByHotTag")
    public Result getBlogInfoByHotTag(@RequestBody BlogSelectParams params){
        BasePage<List> page = blBlogService.getBlogInfoByHotTag(params);
        return new Result(page);
    }

    /**
     * 获取我收藏的帖子
     * @param params
     * @return
     */
    @RequiresPermissions("user-all")
    @RequestMapping("getMyCollect")
    public Result getMyCollect(@RequestBody BlogSelectParams params){
        BasePage<List> page = blBlogService.getMyCollect(params);
        return new Result(page);
    }

    /**
     * 获取我的帖子
     * @param params
     * @return
     */
    @RequiresPermissions("user-all")
    @RequestMapping("getMyBlog")
    public Result getMyBlog(@RequestBody BlogSelectParams params){
        BasePage<List> page = blBlogService.getMyBlog(params);
        return new Result(page);
    }

    /**
     * 根据帖子ID查询帖子信息
     * @param blogId 帖子ID
     * @return
     */
    @RequiresPermissions("user-all")
    @RequestMapping("getBlogInfoById")
    public Result getBlogInfoById(String blogId){
        if (null == blogId){
            throw new BaseException(ResultEnum.PARAMS_NULL);
        }
        Map<String, Object> map = blBlogService.getBlogInfoById(blogId);
        return new Result(map);
    }

    /**
     * 根据帖子类型分页查询帖子
     * @param pageSize 单页数据
     * @param currentPage 当前页
     * @param activeType 帖子类型
     * @return 返回帖子集合
     */
    @RequiresPermissions("user-all")
    @RequestMapping("getBlogInType")
    public Result getBlogInType(Integer pageSize, Integer currentPage, Integer activeType){
        BasePage<List> basePage = blBlogService.getBlogInType(pageSize, currentPage, activeType);

        return new Result(basePage);
    }

    /**
     * 上传帖子图片
     * @param image 图片文件
     * @return 返回url
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/postBlogImage")
    public Result postBlogImage(MultipartFile image) {
        //审核图片
        contentReview.reviewImage(image);

        String imageUrl = blBlogService.postBlogImage(image);
        Result result = new Result();
        result.setData(imageUrl);
        return result;
    }

    /**
     * 删除帖子图片
     * @param imageUrl 图片url
     * @return 返回是否删除成功
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/deleteBlogImage")
    public Result deleteBlogImage(String imageUrl) {
        if (imageUrl != null) {
            blBlogService.deleteBlogImage(imageUrl);
        }

        return new Result(ResultEnum.SUCCESS);
    }

    /**
     * 上传帖子
     * @param blBlog 帖子实体
     * @return
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/add")
    public Result addBlog(@RequestBody BlBlog blBlog) {
        //审核
        contentReview.reviewText(blBlog.getBlogContent()  + blBlog.getBlogTitle());

        if (null == blBlog){
            throw new BaseException(ResultEnum.PARAMS_ERROR);
        }

        boolean res = blBlogService.addBlog(blBlog);
        return new Result(ResultEnum.SUCCESS);
    }
}