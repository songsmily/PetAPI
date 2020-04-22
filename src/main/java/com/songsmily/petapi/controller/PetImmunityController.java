package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetImmunity;
import com.songsmily.petapi.service.PetImmunityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * (PetImmunity)表控制层
 *
 * @author SongSmily
 * @since 2020-03-20 09:00:20
 */
@RestController
@RequestMapping("petImmunity")
public class PetImmunityController  {
    /**
     * 服务对象
     */
    @Resource
    private PetImmunityService petImmunityService;

    @RequiresPermissions("user-all")
    @RequestMapping("insertImmunity")
    public Result insertImmnuity(@RequestBody PetImmunity petImmunity){
        return petImmunityService.insertImmnuity(petImmunity);
    }


    @RequiresPermissions("user-all")
    @RequestMapping("updateImmunity")
    public Result updateImmunity(@RequestBody PetImmunity petImmunity){
        return petImmunityService.updateImmunity(petImmunity);
    }

    /**
     * 根据petId查询防疫信息
     * @param petId
     * @return
     */
    @RequiresPermissions("user-all")
    @RequestMapping("getImmunityById")
    public Result getImmunityById(String petId) {
        List<PetImmunity> list = petImmunityService.getImmunityById(petId);
        return new Result(list);
    }

    /**
     * 根据immunityID查询防疫信息
     * @param immunityId
     * @return
     */
    @RequiresPermissions("user-all")
    @RequestMapping("getImmunityByImmunityId")
    public Result getImmunityByImmunityId(String immunityId) {
        PetImmunity immunity = petImmunityService.getImmunityInfoByImmunityId(immunityId);
        return new Result(immunity);
    }
}