package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Build;

/**
 * 楼栋名称表(Build)表服务接口
 *
 * @author SongSmily
 * @since 2019-12-18 19:19:41
 */
public interface BuildService extends IService<Build> {

    Result getUserLocationArray(String build_name);
}