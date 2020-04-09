package com.songsmily.petapi.controller;

import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.GoodService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("good")
public class GoodController {
    @Resource
    GoodService goodService;

    @RequiresPermissions("user-all")
    @RequestMapping("addBlogGood")
    public Result addBlogGood(String blogId){
        if (null == blogId)
            throw new BaseException(ResultEnum.PARAMS_NULL);
        boolean res = goodService.addBlogGood(blogId);
        return new Result(ResultEnum.SUCCESS);
    }
    @RequiresPermissions("user-all")
    @RequestMapping("removeBlogGood")
    public Result removeBlogGood(String blogId){
        if (null == blogId)
            throw new BaseException(ResultEnum.PARAMS_NULL);
        boolean res = goodService.removeBlogGood(blogId);
        if (res)
            return new Result(ResultEnum.SUCCESS);
        else
            return new Result(ResultEnum.ERROR);
    }
}
