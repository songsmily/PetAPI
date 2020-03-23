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

}