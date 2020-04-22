package com.songsmily.petapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.songsmily.petapi.dao.*;
import com.songsmily.petapi.dto.PetAllInfoSelectParams;
import com.songsmily.petapi.dto.PetNoticeMessage;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.*;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.PetManageService;
import com.songsmily.petapi.utils.MessageUtils;
import org.omg.CORBA.OBJECT_NOT_EXIST;
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

    @Resource
    MessageUtils messageUtils;

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
                        if (petImmunity.getImmunityStatus() == 1)
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

    /**
     * 向用户发送宠物免疫信息注射及建议信息
     * @param message
     * @return
     */
    @Override
    public boolean sendImmunityMessage(PetNoticeMessage message) {
        Map<String,String> map = new HashMap<>();
        map.put("number", message.getPhone());
        StringBuilder builder = new StringBuilder();
        builder.append("宠物之家用户您好，系统提示：请及时对您饲养的宠物“" ).append(message.getPetName()).append("“").append("注射疫苗并上传疫苗信息到系统！");
        builder.append("宠物管理员建议：").append(message.getMessage());
        map.put("message", builder.toString());
        boolean b = messageUtils.sendMessage(map);
        return b;
    }

    /**
     * 向用户发送免疫证书信息上传通知
     * @param message
     * @return
     */
    @Override
    public boolean sendCardMessage(PetNoticeMessage message) {
        Map<String,String> map = new HashMap<>();
        map.put("number", message.getPhone());
        StringBuilder builder = new StringBuilder();
        builder.append("宠物之家用户您好，系统提示：请及时为您饲养的宠物“" ).append(message.getPetName()).append("”办理宠物免疫证书并将证书信息上传至系统！");
        map.put("message", builder.toString());
        boolean b = messageUtils.sendMessage(map);
        return b;
    }

    /**
     * 查询宠物信息总览
     * @return
     */
    @Override
    public Map<String, Object> getOverView() {
        QueryWrapper wrapper;
        //查询已审核宠物总数
        wrapper = new QueryWrapper();
        wrapper.eq("pet_status", 1);
        wrapper.eq("is_cancel", 0);
        Integer checkPetCount = petinfoDao.selectCount(wrapper);

        //查询待审核宠物总数
        wrapper = new QueryWrapper();
        wrapper.eq("pet_status", 0);
        wrapper.eq("is_cancel", 0);
        Integer unCheckPetCount  = petinfoDao.selectCount(wrapper);

        //查询已审核宠物证书总数
        wrapper = new QueryWrapper();
        wrapper.eq("card_status", 1);
        wrapper.eq("is_cancel", 0);
        Integer checkCardCount= petCardDao.selectCount(wrapper);

        //查询待审核宠物证书总数
        wrapper = new QueryWrapper();
        wrapper.eq("card_status", 0);
        wrapper.eq("is_cancel", 0);
        Integer unCheckCardCount = petCardDao.selectCount(wrapper);

        //查询已审核疫苗信息总数
        wrapper = new QueryWrapper();
        wrapper.eq("immunity_status", 1);
        wrapper.eq("is_cancel", 0);
        Integer checkImmunityCount= petImmunityDao.selectCount(wrapper);

        //查询待审核疫苗信息总数
        wrapper = new QueryWrapper();
        wrapper.eq("immunity_status", 0);
        wrapper.eq("is_cancel", 0);
        Integer unCheckImmunityCount= petImmunityDao.selectCount(wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("checkPetCount", checkPetCount);
        map.put("unCheckPetCount", unCheckPetCount);
        map.put("checkCardCount", checkCardCount);
        map.put("unCheckCardCount", unCheckCardCount);
        map.put("checkImmunityCount", checkImmunityCount);
        map.put("unCheckImmunityCount", unCheckImmunityCount);

        return map;
    }
}
