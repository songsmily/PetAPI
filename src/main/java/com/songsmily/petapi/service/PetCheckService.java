package com.songsmily.petapi.service;

import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCard;

public interface PetCheckService {
    Result selectUnCheckList(Integer currentPage, Integer pageSize, String areaFilter);

    Result getPetAndHostInfoById(Integer petId);

    Result doCheckFalse(Integer petId, String falseRes);

    Result doCheckPass(Integer petId);

    Result returnUnCheckCardPetInfo(Integer currentPage, Integer pageSize, String areaFilter);

    Result doCheckCard(PetCard petCard, boolean b);
}
