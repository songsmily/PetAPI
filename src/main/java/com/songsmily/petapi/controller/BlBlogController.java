package com.songsmily.petapi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.BlBlog;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.BlBlogService;
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
        System.out.println(pageSize);
        System.out.println(currentPage);
        System.out.println(activeType);
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
        System.out.println(imageUrl);
        blBlogService.deleteBlogImage(imageUrl);

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
        if (null == blBlog){
            throw new BaseException(ResultEnum.PARAMS_ERROR);
        }

        boolean res = blBlogService.addBlog(blBlog);
        return new Result(ResultEnum.SUCCESS);
    }
}