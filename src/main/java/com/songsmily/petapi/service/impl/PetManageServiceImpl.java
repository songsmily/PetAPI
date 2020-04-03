package com.songsmily.petapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.songsmily.petapi.dao.*;
import com.songsmily.petapi.dto.PetAllInfoSelectParams;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.*;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.PetManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("petManageService")
public class PetManageServiceImpl implements PetManageService {

    @Resource
    PetinfoDao petinfoDao;
    @Resource
    UserDao userDao;
    @Resource
    PetCardDao petCardDao;
    @Resource
    PetImmunityDao petImmunityDao;
    @Resource
    SysPetNoticeDao sysPetNoticeDao;
    @Resource
    PetCancelDao petCancelDao;

    @Override
    public Result returnPetCardImmunityInfoList(PetAllInfoSelectParams petAllInfoSelectParams) {
        Map<String, Object> selectMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        List<Petinfo> petinfos;
        selectMap.put("begin", (petAllInfoSelectParams.getCurrentPage() - 1) * petAllInfoSelectParams.getPageSize());
        selectMap.put("begin", (petAllInfoSelectParams.getCurrentPage() - 1) * petAllInfoSelectParams.getPageSize());
        selectMap.put("end", petAllInfoSelectParams.getPageSize());
        selectMap.put("petStatus", petAllInfoSelectParams.getPetStatus());
        selectMap.put("petCardStatus", petAllInfoSelectParams.getPetCardStatus());
        selectMap.put("petImmunityStatus", petAllInfoSelectParams.getPetImmunityStatus());
        Integer total = 0;
        if (petAllInfoSelectParams.getAreaFilter().equals("-1")) {
            selectMap.put("userIDs", null);
            petinfos = petinfoDao.petManageSelectPetInfoStepOneReturnPetAndPetCardInfo(selectMap);
            total = petinfoDao.petManageSelectPetInfoStepOneReturnPetAndPetCardInfoCount(selectMap);
        } else {
            List<String> userIDs = userDao.selectUserIdByArea(petAllInfoSelectParams.getAreaFilter());
            if (userIDs.size() > 0) {
                selectMap.put("userIDs", userIDs);
                petinfos = petinfoDao.petManageSelectPetInfoStepOneReturnPetAndPetCardInfo(selectMap);
                total = petinfoDao.petManageSelectPetInfoStepOneReturnPetAndPetCardInfoCount(selectMap);
            } else {
                petinfos = null;
                total = 0;
            }
        }
        map.put("total", total);
        map.put("pages", Math.ceil(total / petAllInfoSelectParams.getPageSize()));
        Map<String, Object> petMap = new HashMap<>();
        Set<Integer> userInfoIDs = new HashSet<>();
        Set<Integer> petCardInfoIDs = new HashSet<>();
        List<Map> records = new ArrayList<>();
        if (petinfos != null) {
            for (Petinfo petinfo :
                    petinfos) {
                userInfoIDs.add(petinfo.getHosteId());
                petCardInfoIDs.add(petinfo.getPetId());
            }
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.in("id", userInfoIDs);
            List<User> userInfos = userDao.selectList(wrapper);
            wrapper = new QueryWrapper();
            wrapper.in("pet_id", petCardInfoIDs);
            List<PetImmunity> petImmunities = petImmunityDao.selectList(wrapper);

            for (Petinfo petinfo :
                    petinfos) {
                Map<String, Object> itemMap = new HashMap<>();
                User userInfo = new User();
                List<PetImmunity> petImmunityInfo = new ArrayList<>();
                Integer petImmunityCount = 0;

                for (User user :
                        userInfos) {
                    if (petinfo.getHosteId().equals(user.getId())) {
                        userInfo = user;
                        break;
                    }
                }
                for (PetImmunity petImmunity :
                        petImmunities) {
                    if (petImmunity.getPetId().equals(petinfo.getPetId())) {
                        petImmunityInfo.add(petImmunity);
                        petImmunityCount ++;
                    }
                }
                PetCancel petCancel =  null;
                if (petinfo.getIsCancel() ==  1){
                    wrapper = new QueryWrapper();
                    wrapper.eq("pet_id",petinfo.getPetId());
                    petCancel = petCancelDao.selectOne(wrapper);
                }
                itemMap.put("petInfo", petinfo);
                itemMap.put("userInfo", userInfo);
                itemMap.put("petCancel",petCancel);
                itemMap.put("petImmunityInfo", petImmunityInfo);
                itemMap.put("petImmunityCount", petImmunityCount);

                records.add(itemMap);

            }

        }
        map.put("records", records);

        return new Result(map);
    }

    @Override
    public Result insertNotice(SysPetNotice sysPetNotice) {
        sysPetNotice.setNoticeStatus(0);
        sysPetNotice.setGmtCreate(System.currentTimeMillis());
        if (sysPetNoticeDao.insert(sysPetNotice) > 0) {
            return new Result(ResultEnum.SUCCESS);
        } else {
            return new Result(ResultEnum.ERROR);
        }

    }
}
