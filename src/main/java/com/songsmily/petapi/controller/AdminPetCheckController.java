package com.songsmily.petapi.controller;

import com.songsmily.petapi.aspect.MyLog;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCard;
import com.songsmily.petapi.entity.PetImmunity;
import com.songsmily.petapi.entity.SysPetNotice;
import com.songsmily.petapi.service.PetCheckService;
import com.songsmily.petapi.service.PetinfoService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin/petCheck")
public class AdminPetCheckController {
    /**
     * 服务对象
     */
    @Resource
    private PetCheckService petCheckService;

    /**
     * 返回未审核免疫信息 包含宠物信息 免疫证书  免疫信息
     * @return
     */
    @MyLog(value = "宠物管理--查询未审核免疫信息列表")
    @RequiresPermissions("pet-admin")
    @RequestMapping("/returnUnCheckCardImmunityPetInfo")
    public Result returnUnCheckCardImmunityPetInfo(Integer currentPage,Integer pageSize,String areaFilter){

        return petCheckService.returnUnCheckCardImmunityPetInfo(currentPage,pageSize,areaFilter);
    }

    /**
     * 宠物免疫信息审核通过
     * @param petImmunity
     * @return
     */
    @MyLog(value = "宠物管理--免疫信息审核通过")
    @RequiresPermissions("pet-admin")
    @RequestMapping("/checkImmunityPass")
    public Result  checkImmunityPass(@RequestBody PetImmunity petImmunity){
        return petCheckService.doCheckImmunity(petImmunity,true);
    }

    /**
     * 宠物免疫信息审核失败
     * @param petImmunity
     * @return
     */
    @MyLog(value = "宠物管理--免疫信息审核失败")
    @RequiresPermissions("pet-admin")
    @RequestMapping("/checkImmunityFalse")
    public Result  checkImmunityFalse(@RequestBody PetImmunity petImmunity){
        return petCheckService.doCheckImmunity(petImmunity,false);
    }
    /**
     * 宠物免疫证书审核，返回待审核宠物免疫证书信息
     * @return
     */
    @MyLog(value = "宠物管理--查询待审核免疫证书信息")
    @RequiresPermissions("pet-admin")
    @RequestMapping("/returnUnCheckCardPetInfo")
    public Result returnUnCheckCardPetInfo(Integer currentPage,Integer pageSize,String areaFilter){

        return petCheckService.returnUnCheckCardPetInfo(currentPage,pageSize,areaFilter);
    }

    /**
     * 宠物免疫证书审核失败
     * @param petCard
     * @return
     */
    @MyLog(value = "宠物管理--免疫证书信息审核失败")
    @RequiresPermissions("pet-admin")
    @RequestMapping("/checkCardFalse")
    public Result checkCardFalse(@RequestBody PetCard petCard){
        return petCheckService.doCheckCard(petCard,false);
    }
    /**
     * 宠物免疫证书审核通过
     * @param petCard
     * @return
     */
    @MyLog(value = "宠物管理--免疫证书信息审核通过")
    @RequiresPermissions("pet-admin")
    @RequestMapping("/checkCardPass")
    public Result checkCardPass(@RequestBody PetCard petCard){
        return petCheckService.doCheckCard(petCard,true);
    }


    /*
    返回未审核宠物信息
     */
    @MyLog(value = "宠物管理--查询待审核宠物信息列表")
    @RequiresPermissions("pet-admin")
    @RequestMapping("/returnUnCheckList")
    public Result returnUnCheckList(Integer currentPage, Integer pageSize,String areaFilter){
        return petCheckService.selectUnCheckList(currentPage,pageSize,areaFilter);
    }
    /*
    返回宠物信息和属主信息
     */
    @MyLog(value = "宠物管理--查询所有宠物信息及其属主信息")
    @RequiresPermissions("pet-admin")
    @RequestMapping("/getPetAndHostInfoById")
    public Result getPetAndHostInfoById(Integer petId){
        return petCheckService.getPetAndHostInfoById(petId);
    }
    /*
    审核失败操作
     */
    @MyLog(value = "宠物管理--宠物信息审核失败")
    @RequiresPermissions("pet-admin")
    @RequestMapping("/checkFalse")
    public Result doCheckFalse(Integer petId,String falseRes){
        return petCheckService.doCheckFalse(petId,falseRes);
    }
     /*
    审核通过操作
     */
     @MyLog(value = "宠物管理--宠物信息审核通过")
     @RequiresPermissions("pet-admin")
    @RequestMapping("/checkPass")
    public Result doCheckPass(Integer petId){
        return petCheckService.doCheckPass(petId);
    }

}
