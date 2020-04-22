package com.songsmily.petapi.controller;

import com.songsmily.petapi.aspect.MyLog;
import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.dto.BlogSelectParams;
import com.songsmily.petapi.dto.CommentSelectParams;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.BlBlog;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.BlCheckService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("admin/blCheck")
public class AdminBlCheckController {

    @Resource
    BlCheckService blCheckService;

    /**
     * 二级评论审核失败  删除
     * @param commentSecId
     * @return
     */
    @MyLog(value = "社区管理--删除二级评论")
    @RequiresRoles("community-admin")
    @RequestMapping("commentSecDelete")
    public Result commentSecDelete(String commentSecId) {

        blCheckService.doCommentSecDelete(commentSecId);

        return new Result(ResultEnum.SUCCESS);
    } /**
     * 二级评论审核通过
     * @param commentSecId
     * @return
     */
    @MyLog(value = "社区管理--二级评论审核通过")
    @RequiresRoles("community-admin")
    @RequestMapping("commentSecPass")
    public Result commentSecPass(String commentSecId) {

        boolean res = blCheckService.doCommentSecPass(commentSecId);

        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }
    /**
     * 一级评论审核失败  删除
     * @param commentId
     * @return
     */
    @MyLog(value = "社区管理--删除一级评论")
    @RequiresRoles("community-admin")
    @RequestMapping("commentDelete")
    public Result commentDelete(String commentId) {

        blCheckService.doCommentDelete(commentId);

        return new Result(ResultEnum.SUCCESS);
    } /**
     * 一级评论审核通过
     * @param commentId
     * @return
     */
    @MyLog(value = "社区管理--一级评论审核通过")
    @RequiresRoles("community-admin")
    @RequestMapping("commentPass")
    public Result commentPass(String commentId) {

        boolean res = blCheckService.doCommentPass(commentId);

        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }
    /**
     * 查询评论信息
     * @param params
     * @return
     */
    @MyLog(value = "社区管理--查询评论信息列表")
    @RequiresRoles("community-admin")
    @RequestMapping("getComments")
    public Result getComments(@RequestBody CommentSelectParams params){
        BasePage page = blCheckService.getComments(params);
        return new Result(page);
    }


    /**
     * 帖子审核失败 删除帖子 及其评论相关信息
     * @param blogId
     * @return
     */
    @MyLog(value = "社区管理--删除帖子")
    @RequiresRoles("community-admin")
    @RequestMapping("blogDelete")
    public Result doBlogDelete(String blogId){
        if (null == blogId) {
            throw new BaseException(ResultEnum.PARAMS_NULL);
        }

        boolean res = blCheckService.doBlogDelete(blogId);

        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }
    /**
     * 帖子审核通过
     * @param blogId
     * @return
     */
    @MyLog(value = "社区管理--帖子审核通过")
    @RequiresRoles("community-admin")
    @RequestMapping("blogPass")
    public Result doBlogPass(String blogId){
        if (null == blogId) {
            throw new BaseException(ResultEnum.PARAMS_NULL);
        }
        boolean res = blCheckService.doBlogPass(blogId);
        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }

    /**
     * 获取帖子信息
     * @param params
     * @return
     */
    @MyLog(value = "社区管理--查询帖子信息列表")
    @RequiresRoles("community-admin")
    @RequestMapping("getBlogs")
    public Result getBlogs(@RequestBody BlogSelectParams params) {
        BasePage page = blCheckService.getBlogs(params);
        return new Result(page);
    }
}
