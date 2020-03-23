package com.songsmily.petapi.controller;

import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.service.PlotService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin/plot")
public class AdminPlotController {
    @Resource
    PlotService plotService;

    @RequiresPermissions("pet-admin")
    @RequestMapping("getPlotAndBuild")
    public Result getPlotAndBuild(){
        return  plotService.getPlotAndBuild();
    }

}
