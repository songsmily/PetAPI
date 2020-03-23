package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCard;
import com.songsmily.petapi.service.PetCardService;
import com.songsmily.petapi.utils.BASE64DecodedMultipartFile;
import com.songsmily.petapi.utils.OssUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (PetCard)表控制层
 *
 * @author SongSmily
 * @since 2020-03-20 09:00:47
 */
@RestController
@RequestMapping("petCard")
public class PetCardController {
    /**
     * 服务对象
     */
    @Resource
    private PetCardService petCardService;

    /**
     * 返回宠物信息、免疫证书信息和免疫信息
     * @return
     */
    @RequiresPermissions("user-all")
    @RequestMapping("returnPetCardImmunityInfos")
    public Result returnPetCardImmunityInfos(){
        return petCardService.returnPetCardImmunityInfos();
    }

    /**
     * 上传宠物免疫证书
     * @param petCard
     * @return
     */
    @RequiresPermissions("user-all")
    @RequestMapping("uploadPetCard")
    public Result uploadPetCard(@RequestBody PetCard petCard){

        return petCardService.insertPetCard(petCard);
    }

    /**
     * 修改宠物免疫证书信息
     * @param petCard
     * @return
     */
    @RequiresPermissions("user-all")
    @RequestMapping("reUploadPetCard")
    public Result reUploadPetCard(@RequestBody PetCard petCard){

        return petCardService.updatePetCard(petCard);
    }

}