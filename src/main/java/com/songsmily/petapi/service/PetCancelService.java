package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCancel;

/**
 * (PetCancel)表服务接口
 *
 * @author SongSmily
 * @since 2020-04-02 04:41:38
 */
public interface PetCancelService extends IService<PetCancel> {

    Result doCancel(PetCancel petCancel);
}