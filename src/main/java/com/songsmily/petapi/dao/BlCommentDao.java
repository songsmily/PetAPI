package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.BlComment;

import java.util.List;

/**
 * 评论表(BlComment)表数据库访问层
 *
 * @author SongSmily
 * @since 2020-04-07 15:44:18
 */
public interface BlCommentDao extends BaseMapper<BlComment> {

    List<BlComment> selectCommentAndSecondComment(String blogId);
}