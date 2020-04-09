package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.BlCollect;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.BlCollectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 收藏(BlCollect)表控制层
 *
 * @author SongSmily
 * @since 2020-04-09 09:26:36
 */
@RestController
@RequestMapping("blCollect")
public class BlCollectController {
    /**
     * 服务对象
     */
    @Resource
    private BlCollectService blCollectService;

    @RequiresPermissions(("user-all"))
    @RequestMapping("addBlogConnect")
    public Result addBlogConnect(@RequestBody BlCollect collect) {
        if (null == collect.getBlogId()) {
            throw new BaseException(ResultEnum.PARAMS_NULL);
        }
        boolean res = blCollectService.addBlogConnect(collect);
        return new Result(ResultEnum.SUCCESS);
    }
    @RequiresPermissions(("user-all"))
    @RequestMapping("removeBlogConnect")
    public Result removeBlogConnect(@RequestBody BlCollect collect) {
        if (null == collect.getBlogId()) {
            throw new BaseException(ResultEnum.PARAMS_NULL);
        }
        boolean res = blCollectService.removeBlogConnect(collect);
        return new Result(ResultEnum.SUCCESS);
    }
}