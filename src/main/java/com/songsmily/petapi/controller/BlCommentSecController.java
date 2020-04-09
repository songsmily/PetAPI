package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.BlCommentSec;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.BlCommentSecService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 二级评论表(BlCommentSec)表控制层
 *
 * @author SongSmily
 * @since 2020-04-08 10:36:33
 */
@RestController
@RequestMapping("blCommentSec")
public class BlCommentSecController {
    /**
     * 服务对象
     */
    @Resource
    private BlCommentSecService blCommentSecService;

    @RequiresPermissions("user-all")
    @RequestMapping("addSecComment")
    public Result addSecComment(@RequestBody BlCommentSec commentSec){
        if (null == commentSec.getSecContent() || null == commentSec.getSecParent()){
            throw new BaseException(ResultEnum.PARAMS_NULL);
        }
        boolean res = blCommentSecService.addSecComment(commentSec);
        if (res){
            return new Result(ResultEnum.SUCCESS);
        }
        return new Result(ResultEnum.ERROR);
    }
}
