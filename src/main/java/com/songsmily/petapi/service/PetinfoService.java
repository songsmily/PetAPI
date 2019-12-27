package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Petinfo;

/**
 * 宠物信息表(Petinfo)表服务接口
 *
 * @author SongSmily
 * @since 2019-12-20 17:39:00
 */
public interface PetinfoService extends IService<Petinfo> {

    Result doUpload(Petinfo petinfo);

    Result selectAllPetInfos();

    Result selectById(String petId);
}