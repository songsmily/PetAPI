package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCard;

/**
 * (PetCard)表服务接口
 *
 * @author SongSmily
 * @since 2020-03-20 09:00:47
 */
public interface PetCardService extends IService<PetCard> {

    Result returnPetCardImmunityInfos();

    Result insertPetCard(PetCard petCard);

    Result updatePetCard(PetCard petCard);
}