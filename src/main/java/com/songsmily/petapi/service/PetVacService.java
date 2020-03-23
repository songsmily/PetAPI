package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetVac;

/**
 * (PetVac)表服务接口
 *
 * @author SongSmily
 * @since 2020-03-20 16:13:22
 */
public interface PetVacService extends IService<PetVac> {

    Result returnPetVac(Integer petId);
}