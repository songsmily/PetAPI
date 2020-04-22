package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.entity.BlTag;

import java.util.List;

/**
 * (BlTag)表服务接口
 *
 * @author SongSmily
 * @since 2020-04-10 14:22:55
 */
public interface BlTagService extends IService<BlTag> {

    List<BlTag> getTags();

    boolean addTag(BlTag blTag);

    List<BlTag> getHotTags();
}