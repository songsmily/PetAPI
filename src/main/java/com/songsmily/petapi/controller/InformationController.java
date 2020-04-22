package com.songsmily.petapi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.dto.BlogSelectParams;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Information;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.InformationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/information")
public class InformationController {
    /**
     * 服务对象
     */
    @Resource
    private InformationService informationService;

    @RequiresPermissions("user-all")
    @RequestMapping("getInfoById")
    public Result getInfoById(String infoId) {
        if (null == infoId) {
            throw new BaseException(ResultEnum.PARAMS_NULL);
        }
        Information information = informationService.getById(infoId);

        return new Result(information);
    }

    /**
     * 获取资讯列表
     * @param params
     * @return
     */
    @RequiresPermissions("user-all")
    @RequestMapping("getInfos")
    public Result getInfos(@RequestBody  BlogSelectParams params) {
        IPage page =  informationService.getInfos(params);
        return new Result(page);
    }
}
