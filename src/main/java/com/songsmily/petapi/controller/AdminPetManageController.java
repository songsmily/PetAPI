package com.songsmily.petapi.controller;

import com.songsmily.petapi.aspect.MyLog;
import com.songsmily.petapi.dto.PetAllInfoSelectParams;
import com.songsmily.petapi.dto.PetNoticeMessage;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCardImmunityInfo;
import com.songsmily.petapi.entity.SysPetNotice;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.PetManageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/admin/petManage")
public class AdminPetManageController {
    @Resource
    PetManageService petManageService;

    @MyLog(value = "宠物管理--查询宠物信息总览")
    @RequiresPermissions("pet-admin")
    @RequestMapping("getOverView")
    public Result getOverView() {
        Map<String, Object> map = petManageService.getOverView();
        return new Result(map);
    }


    @MyLog(value = "宠物管理--向用户发送疫苗注射通知及建议信息")
    @RequiresPermissions("pet-admin")
    @RequestMapping("sendCardMessage")
    public Result sendCardMessage(@RequestBody PetNoticeMessage message) {
        boolean res =  petManageService.sendCardMessage(message);
        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }

    @MyLog(value = "宠物管理--向用户发送疫苗注射通知及建议信息")
    @RequiresPermissions("pet-admin")
    @RequestMapping("sendImmunityMessage")
    public Result sendImmunityMessage(@RequestBody PetNoticeMessage message) {
        boolean res =  petManageService.sendImmunityMessage(message);
        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }


    /**
     * 宠物管理 返回宠物信息  与其宠物证书和免疫信息
     * @return
     */
    @MyLog(value = "宠物管理--查询宠物信息、免疫证书以及免疫信息")
    @RequiresPermissions("pet-admin")
    @RequestMapping("returnPetCardImmunityInfoList")
    public Result returnPetCardImmunityInfoList(@RequestBody PetAllInfoSelectParams petAllInfoSelectParams){
        return petManageService.returnPetCardImmunityInfoList(petAllInfoSelectParams);
    }

    /**
     * 宠物管理 给用户发送提示信息 包括提示用户上传宠物免疫证书及免疫信息
     */
    @MyLog(value = "宠物管理--向用户发送通知")
    @RequiresPermissions("pet-admin")
    @RequestMapping("sendNotice")
    public Result sendNotice(@RequestBody SysPetNotice sysPetNotice){
        return petManageService.insertNotice(sysPetNotice);
    }
}
