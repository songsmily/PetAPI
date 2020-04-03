package com.songsmily.petapi.service;

import com.songsmily.petapi.dto.PetAllInfoSelectParams;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCardImmunityInfo;
import com.songsmily.petapi.entity.SysPetNotice;
import org.springframework.stereotype.Service;

@Service
public interface PetManageService {
    Result returnPetCardImmunityInfoList(PetAllInfoSelectParams petAllInfoSelectParams);

    Result insertNotice(SysPetNotice sysPetNotice);
}
