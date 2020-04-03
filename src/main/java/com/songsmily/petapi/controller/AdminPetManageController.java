package com.songsmily.petapi.controller;

import com.songsmily.petapi.dto.PetAllInfoSelectParams;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCardImmunityInfo;
import com.songsmily.petapi.entity.SysPetNotice;
import com.songsmily.petapi.service.PetManageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/petManage")
public class AdminPetManageController {
    @Resource
    PetManageService petManageService;

    /**
     * 宠物管理 返回宠物信息  与其宠物证书和免疫信息

     * @return
     */
    @RequiresPermissions("pet-admin")
    @RequestMapping("returnPetCardImmunityInfoList")
    public Result returnPetCardImmunityInfoList(@RequestBody PetAllInfoSelectParams petAllInfoSelectParams){
        return petManageService.returnPetCardImmunityInfoList(petAllInfoSelectParams);
    }

    /**
     * 宠物管理 给用户发送提示信息 包括提示用户上传宠物免疫证书及免疫信息
     */
    @RequiresPermissions("pet-admin")
    @RequestMapping("sendNotice")
    public Result sendNotice(@RequestBody SysPetNotice sysPetNotice){
        return petManageService.insertNotice(sysPetNotice);
    }
}
