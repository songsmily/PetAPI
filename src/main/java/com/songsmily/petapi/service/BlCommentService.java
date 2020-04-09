package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.entity.BlComment;

import java.util.List;

/**
 * 评论表(BlComment)表服务接口
 *
 * @author SongSmily
 * @since 2020-04-07 15:44:18
 */
public interface BlCommentService extends IService<BlComment> {

    Boolean addComment(BlComment comment);

    List<BlComment> selectComments(String blogId);
}