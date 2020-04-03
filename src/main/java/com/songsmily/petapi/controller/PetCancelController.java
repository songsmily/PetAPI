package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCancel;
import com.songsmily.petapi.service.PetCancelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (PetCancel)表控制层
 *
 * @author SongSmily
 * @since 2020-04-02 04:41:38
 */
@RestController
@RequestMapping("petCancel")
public class PetCancelController {
    /**
     * 服务对象
     */
    @Resource
    private PetCancelService petCancelService;

    /**
     * 上传宠物注销信息
     * @param petCancel 宠物注销信息实体对象
     * @return Result 封装实体
     */
    @RequiresPermissions("user-all")
    @RequestMapping("doCancel")
    public Result doCancel(@RequestBody PetCancel petCancel) {
        return petCancelService.doCancel(petCancel);
    }

}