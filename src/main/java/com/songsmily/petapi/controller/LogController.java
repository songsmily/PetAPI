package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.dto.SelectPageParams;
import com.songsmily.petapi.entity.Log;
import com.songsmily.petapi.service.LogService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * (Log)表控制层
 *
 * @author SongSmily
 * @since 2020-04-17 07:39:25
 */
@RestController
@RequestMapping("/admin/log")
public class LogController  {
    /**
     * 服务对象
     */
    @Resource
    private LogService logService;

    /**
     * 查询日志列表
     * @param params
     * @return
     */
    @RequiresRoles("sys-admin")
    @RequestMapping("getLogList")
    public Result getLogList(@RequestBody  SelectPageParams params) {
        IPage page = logService.getLogList(params);
        return new Result(page);
    }

    /**
     * 查询日志中的用户名列表
     * @return
     */
    @RequiresRoles("sys-admin")
    @RequestMapping("getLogUserNameList")
    public Result getLogUserNameLists() {
        List<Map> list = logService.getLogUserNameList();
        return new Result(list);
    }


}