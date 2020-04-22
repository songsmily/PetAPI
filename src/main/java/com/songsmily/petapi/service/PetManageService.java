package com.songsmily.petapi.service;

import com.songsmily.petapi.dto.PetAllInfoSelectParams;
import com.songsmily.petapi.dto.PetNoticeMessage;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCardImmunityInfo;
import com.songsmily.petapi.entity.SysPetNotice;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PetManageService {
    Result returnPetCardImmunityInfoList(PetAllInfoSelectParams petAllInfoSelectParams);

    Result insertNotice(SysPetNotice sysPetNotice);

    boolean sendImmunityMessage(PetNoticeMessage message);

    boolean sendCardMessage(PetNoticeMessage message);


    Map<String, Object> getOverView();

}
