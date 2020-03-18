package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Pettype;

/**
 * (Pettype)表服务接口
 *
 * @author SongSmily
 * @since 2019-12-20 19:30:28
 */
public interface PettypeService extends IService<Pettype> {

    Result getPetTypeArray();


    Result getPetTypeById(Integer petTypeId);
}