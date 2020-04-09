package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.entity.BlCommentSec;

/**
 * 评论表(BlCommentSec)表服务接口
 *
 * @author SongSmily
 * @since 2020-04-08 10:36:33
 */
public interface BlCommentSecService extends IService<BlCommentSec> {

    boolean addSecComment(BlCommentSec commentSec);
}