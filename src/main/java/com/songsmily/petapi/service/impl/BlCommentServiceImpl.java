package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.BlBlogDao;
import com.songsmily.petapi.dao.BlCommentDao;
import com.songsmily.petapi.entity.BlBlog;
import com.songsmily.petapi.entity.BlComment;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.BlCommentService;
import com.songsmily.petapi.utils.ShiroUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 评论表(BlComment)表服务实现类
 *
 * @author SongSmily
 * @since 2020-04-07 15:44:18
 */
@Service("blCommentService")
public class BlCommentServiceImpl extends ServiceImpl<BlCommentDao, BlComment> implements BlCommentService {
    @Resource
    BlCommentDao blCommentDao;
    @Resource
    BlBlogDao blBlogDao;
    @Override
    @Transactional
    public Boolean addComment(BlComment comment) {
        User user = ShiroUtil.getUser(new User());
        comment.setCommentUser(user.getId());
        comment.setCreatedTime(System.currentTimeMillis());
        comment.setCommentId(UUID.randomUUID().toString());
        BlBlog blog = blBlogDao.selectById(comment.getCommentBlog());
        blog.setBlogComment(blog.getBlogComment() + 1);
        int i = blBlogDao.updateById(blog);
        int insert = blCommentDao.insert(comment);
        if (insert < 1 || i < 1){
            throw new BaseException(ResultEnum.ERROR);
        }

        return true;
    }

    @Override
    public List<BlComment> selectComments(String blogId) {
        List<BlComment> comments = blCommentDao.selectCommentAndSecondComment(blogId);
        return comments;
    }
}