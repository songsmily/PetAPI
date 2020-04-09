package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.BlCommentDao;
import com.songsmily.petapi.dao.BlCommentSecDao;
import com.songsmily.petapi.entity.BlComment;
import com.songsmily.petapi.entity.BlCommentSec;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.BlCommentSecService;
import com.songsmily.petapi.utils.ShiroUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 二级评论表(BlCommentSec)表服务实现类
 *
 * @author SongSmily
 * @since 2020-04-08 10:36:33
 */
@Service("blCommentSecService")
public class BlCommentSecServiceImpl extends ServiceImpl<BlCommentSecDao, BlCommentSec> implements BlCommentSecService {
    @Resource
    BlCommentSecDao blCommentSecDao;
    @Resource
    BlCommentDao blCommentDao;

    @Transactional
    @Override
    public boolean addSecComment(BlCommentSec commentSec) {
        System.err.println(commentSec);
        commentSec.setCreatedTime(System.currentTimeMillis());
        commentSec.setSecId(UUID.randomUUID().toString());
        commentSec.setSecUser(ShiroUtil.getUser(new User()).getId());

        BlComment comment = blCommentDao.selectById(commentSec.getSecParent());
        comment.setCommentSecond(comment.getCommentSecond() + 1);
        try {
            int insert = blCommentSecDao.insert(commentSec);
            int i = blCommentDao.updateById(comment);

            if (insert > 0 && i > 0){
                return true;
            }
        }catch (Exception e){
            throw new BaseException(ResultEnum.ERROR);
        }

        return false;
    }
}