package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.songsmily.petapi.dao.BlBlogDao;
import com.songsmily.petapi.dao.BlCommentDao;
import com.songsmily.petapi.dao.BlCommentSecDao;
import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.dto.BlogSelectParams;
import com.songsmily.petapi.dto.CommentSelectParams;
import com.songsmily.petapi.entity.BlBlog;
import com.songsmily.petapi.entity.BlComment;
import com.songsmily.petapi.entity.BlCommentSec;
import com.songsmily.petapi.service.BlCheckService;
import com.songsmily.petapi.service.RedisService;
import com.songsmily.petapi.utils.OssUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("BlCheckService")
public class BlCheckServiceImpl implements BlCheckService {

    @Resource
    BlBlogDao blogDao;
    @Resource
    BlCommentDao commentDao;
    @Resource
    BlCommentSecDao commentSecDao;
    @Resource
    RedisService redisService;
    @Resource
    OssUtil ossUtil;

    @Value("${aliyun.oss.imageUrl}")
    private String imageUrl;
    /**
     * 获取帖子列表
     * @param params
     * @return
     */
    @Override
    public BasePage getBlogs(BlogSelectParams params) {
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("blogType",0);
        selectMap.put("begin",(params.getCurrentPage() - 1) * params.getPageSize());
        selectMap.put("end",params.getPageSize());
        selectMap.put("deleted",0);
        selectMap.put("blogStatus",0);

        List<BlBlog> blogList = blogDao.selectBlogPageByType(selectMap);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("deleted",0);
        wrapper.eq("blog_status",0);
        Integer total = blogDao.selectCount(wrapper);

        BasePage page = new BasePage<>();
        page.setRecords(blogList);
        page.setTotal(total);

        return page;
    }

    /**
     * 帖子审核通过
     * @param blogId
     * @return
     */
    @Override
    public boolean doBlogPass(String blogId) {
        Integer res = blogDao.updateBlogStatusById(blogId);
        return res > 0;
    }

    /**
     * 帖子审核失败  删除帖子 评论信息 及redis中相关数据
     * @param blogId
     * @return
     */
    @Transactional
    @Override
    public boolean doBlogDelete(String blogId) {
        //删除帖子评论
        //获取一级评论ID
        List<String> commentIds = commentDao.selectCommentIdsByBlogId(blogId);
        //根据一级评论Id删除二级评论
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.in("sec_parent",commentIds);

        //删除二级评论
        commentSecDao.delete(wrapper);

        //删除一级评论
        wrapper = new QueryWrapper();
        wrapper.in("comment_id",commentIds);
        commentDao.delete(wrapper);

        //删除redis中关于帖子的数据
        redisService.removeBlogALl(blogId);

        //删除帖子下评论相关的redis数据 点赞
        redisService.removeCommentAll(commentIds);

        //将帖子修改为逻辑删除
        BlBlog blog = blogDao.selectById(blogId);
        blog.setDeleted(1);
        blogDao.updateById(blog);
        String content  = blog.getBlogContent();
        List<String> files = new ArrayList<>();

        while (true) {
            if (content.indexOf(imageUrl) != -1) {
                content = content.substring(content.indexOf(imageUrl) + imageUrl.length());
                files.add("images/" + content.substring(0,content.indexOf(")")));
            } else {
                break;
            }
        }
        if (files.size() >  0){
            ossUtil.deleteFile20SS(files);
        }

        return true;
    }

    /**
     * 根据参数查询一级或二级评论信息
     * @param params
     * @return
     */
    @Override
    public BasePage getComments(CommentSelectParams params) {
        BasePage page = new BasePage();
        QueryWrapper wrapper = new QueryWrapper();
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("begin",(params.getCurrentPage() - 1) * params.getPageSize());
        selectMap.put("end",params.getPageSize());


        //查询一级评论
        if (params.getCommentLevel() == 1) {
            wrapper.eq("comment_status",0);
            List<BlComment> list = commentDao.selectComments(selectMap);
            page.setRecords(list);
            int total = commentDao.selectCount(wrapper);
            page.setTotal(total);
        } else {
            wrapper.eq("sec_status",0);
            List<BlCommentSec> list = commentSecDao.selectCommentSecs(selectMap);
            int total = commentSecDao.selectCount(wrapper);
            page.setRecords(list);
            page.setTotal(total);
        }
        return page;
    }

    /**
     * 一级评论审核通过
     * @param commentId
     * @return
     */
    @Override
    public boolean doCommentPass(String commentId) {
        BlComment comment = commentDao.selectById(commentId);
        comment.setCommentStatus(1);

        int i = commentDao.updateById(comment);

        return i > 0;
    }

    /**
     * 一级评论删除 包括二级评论及redis点赞信息
     * @param commentId
     * @return
     */
    @Override
    public boolean doCommentDelete(String commentId) {
        //根据一级评论Id删除二级评论
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("sec_parent",commentId);

        //删除二级评论
        commentSecDao.delete(wrapper);

        //删除redis中所有一级评论点赞 信息
        List<String> list = new ArrayList<>();
        list.add(commentId);
        redisService.removeCommentAll(list);

        //帖子评论数减一
        BlComment comment = commentDao.selectById(commentId);
        BlBlog blog = blogDao.selectById(comment.getCommentBlog());
        blog.setBlogComment(blog.getBlogComment() -  1);
        blogDao.updateById(blog);

        //删除一级评论
        commentDao.deleteById(commentId);


        return true;
    }

    /**
     * 二级评论删除
     * @param commentSecId
     */
    @Override
    public void doCommentSecDelete(String commentSecId) {
        commentSecDao.deleteById(commentSecId);
    }

    /**
     * 二级评论审核通过
     * @param commentSecId
     * @return
     */
    @Override
    public boolean doCommentSecPass(String commentSecId) {
        BlCommentSec commentSec = commentSecDao.selectById(commentSecId);;
        commentSec.setSecStatus(1);
        int i = commentSecDao.updateById(commentSec);

        return i > 0;
    }

}
