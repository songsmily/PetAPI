package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.aspect.MyLog;
import com.songsmily.petapi.dto.BlogSelectParams;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Information;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.BlBlogService;
import com.songsmily.petapi.service.InformationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Information)表控制层
 *
 * @author SongSmily
 * @since 2020-04-14 08:09:45
 */
@RestController
@RequestMapping("admin/information")
public class AdminInformationController {
    /**
     * 服务对象
     */
    @Resource
    private InformationService informationService;

    @Resource
    BlBlogService blogService;

    /**
     * 删除资讯
     * @param infoId
     * @return
     */
    @MyLog(value = "社区管理--删除资讯")
    @RequiresRoles("community-admin")
    @RequestMapping("deleteInfoById")
    public Result deleteInfoById(String infoId) {
        boolean res = informationService.deleteInfoById(infoId);
        return new Result(res ?  ResultEnum.SUCCESS : ResultEnum.ERROR);
    }
    /**
     * 根据ID查询资讯
     * @param infoId
     * @return
     */
    @MyLog(value = "社区管理--查看资讯详情")
    @RequiresRoles("community-admin")
    @RequestMapping("getInfoById")
    public Result getInfoById(String infoId) {
        if (null == infoId) {
            throw new BaseException(ResultEnum.PARAMS_NULL);
        }

        Information information = informationService.getById(infoId);

        return new Result(information);
    }
    /**
     * 获取资讯列表
     * @param params
     * @return
     */
    @MyLog(value = "社区管理--查询资讯列表")
    @RequiresRoles("community-admin")
    @RequestMapping("getInfos")
    public Result getInfos(@RequestBody BlogSelectParams params) {
        IPage page =  informationService.getInfos(params);
        return new Result(page);
    }
    /**
     * 上传资讯
     * @param information
     * @return
     */
    @MyLog(value = "社区管理--新增资讯")
    @RequiresRoles("community-admin")
    @RequestMapping("/add")
    public Result addInformation(@RequestBody Information information) {

        boolean res = informationService.addInformation(information);
        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }

    /**
     * 上传图片
     * @param image
     * @return
     */
    @RequiresRoles("community-admin")
    @RequestMapping("/postInfoImage")
    public Result postInfoImage(MultipartFile image) {
        String imageUrl = blogService.postBlogImage(image);
        Result result = new Result();
        result.setData(imageUrl);
        return result;
    }

    /**
     * 删除图片
     * @param imageUrl
     * @return
     */
    @RequiresRoles("community-admin")
    @RequestMapping("/deleteInfoImage")
    public Result deleteBlogImage(String imageUrl) {
        blogService.deleteBlogImage(imageUrl);
        return new Result(ResultEnum.SUCCESS);
    }



}