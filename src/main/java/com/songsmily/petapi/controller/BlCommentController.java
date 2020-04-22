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
import com.songsmily.petapi.utils.ContentReview;
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

    /**
     * 内容审核对象
     */
    @Resource
    ContentReview contentReview;

    /**
     * 通过帖子ID查询评论
     * @param blogId
     * @return
     */
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
     * @return 返回是否新增成功
     */
    @RequiresPermissions("user-all")
    @RequestMapping("addComment")
    public Result addComment(String commentBlog, String commentContent ){
        //审核
        contentReview.reviewText(commentContent);

        BlComment comment = new BlComment();
        comment.setCommentBlog(commentBlog);
        comment.setCommentContent(commentContent);
        if (null == comment.getCommentContent() || null == comment.getCommentBlog()){
            throw new BaseException(ResultEnum.PARAMS_NULL);
        }
        blCommentService.addComment(comment);
        return new Result(ResultEnum.SUCCESS);
    }

}