package com.songsmily.petapi.service;

import com.songsmily.petapi.dto.Result;

public interface PetCheckService {
    Result selectUnCheckList(Integer currentPage, Integer pageSize);

    Result getPetAndHostInfoById(Integer petId);

    Result doCheckFalse(Integer petId, String falseRes);

    Result doCheckPass(Integer petId);
}
