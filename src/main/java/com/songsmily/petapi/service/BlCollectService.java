package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.entity.BlCollect;

/**
 * 收藏时间(BlCollect)表服务接口
 *
 * @author SongSmily
 * @since 2020-04-09 09:26:36
 */
public interface BlCollectService extends IService<BlCollect> {

    boolean addBlogConnect(BlCollect collect);

    boolean removeBlogConnect(BlCollect collect);

}