package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.BlComment;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.BlCommentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 评论表(BlComment)表控制层
 *
 * @author SongSmily
 * @since 2020-04-07 15:44:18
 */
@RestController
@RequestMapping("blComment")
public class BlCommentController  {
    /**
     * 服务对象
     */
    @Resource
    private BlCommentService blCommentService;

    @RequiresPermissions("user-all")
    @RequestMapping("getBlogCommentById")
    public Result getBlogCommentById(String blogId){
        if (null == blogId){
            throw new BaseException(ResultEnum.PARAMS_NULL);
        }
        List<BlComment> comments = blCommentService.selectComments(blogId);
        return new Result(comments);
    }

    /**
     * 新增评论
     * @param comment 评论实体
     * @return 返回是否新增成功
     */
    @RequiresPermissions("user-all")
    @RequestMapping("addComment")
    public Result addComment(@RequestBody BlComment comment){
        if (null == comment.getCommentContent() || null == comment.getCommentBlog()){
            throw new BaseException(ResultEnum.PARAMS_NULL);
        }
        blCommentService.addComment(comment);
        return new Result(ResultEnum.SUCCESS);
    }

}