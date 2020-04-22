package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetImmunity;

import java.util.List;
import java.util.Map;

/**
 * (PetImmunity)表服务接口
 *
 * @author SongSmily
 * @since 2020-03-20 09:00:20
 */
public interface PetImmunityService extends IService<PetImmunity> {

    Result insertImmnuity(PetImmunity petImmunity);

    Result updateImmunity(PetImmunity petImmunity);

    List<PetImmunity> getImmunityById(String petId);

    PetImmunity getImmunityInfoByImmunityId(String immunityId);
}