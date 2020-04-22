package com.songsmily.petapi.controller;

import com.songsmily.petapi.aspect.MyLog;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.service.AdminCommunityService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 社区管理 数据概览
 */
@RequestMapping("/admin/community")
@RestController
public class AdminCommunityController {

    @Resource
    AdminCommunityService communityService;

    /**
     * 获取社区总览信息
     * @return
     */
    @MyLog(value = "社区管理--查询社区总览信息")
    @RequiresRoles("community-admin")
    @RequestMapping("getOverView")
    public Result getOverView() {
        Map<String, Object> map = communityService.getOverView();

        return new Result(map);
    }


}
