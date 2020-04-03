package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Petinfo;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.PetinfoService;
import com.songsmily.petapi.utils.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 宠物信息表(Petinfo)表控制层
 *
 * @author SongSmily
 * @since 2019-12-20 17:39:00
 */
@RestController
@RequestMapping("petinfo")
public class PetinfoController {
    /**
     * 服务对象
     */
    @Resource
    private PetinfoService petinfoService;
    /**
     * 根据id查询宠物详细信息
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/getPetInfoById")
    public Result getPetInfoById(String petId) {
        return petinfoService.selectById(petId);
    }

    /**
     * 返回用户下所有宠物信息
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/returnPetInfos")
    public Result returnPetInfos(){
        return petinfoService.selectAllPetInfos();
    }
    /**
     * 上传宠物信息
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/doUpload")
    public Result doUpload(@RequestBody Petinfo petinfo){
        return petinfoService.doUpload(petinfo);
    }

    /**
     * 修改宠物信息
     */
    @RequiresPermissions("user-all")
    @RequestMapping("doReUpload")
    public Result doReUpload(@RequestBody Petinfo petinfo){
        return petinfoService.doReUpload(petinfo);
    }
    /**
     * 删除宠物信息
     */
    @RequiresPermissions("user-all")
    @RequestMapping("deletePetInfoById")
    public Result deletePetInfoById( Integer petId){
        return petinfoService.deletePetInfoById(petId);
    }



}