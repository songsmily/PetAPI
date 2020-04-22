package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.aspect.MyLog;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.AdminUser;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.AdminUserService;
import com.songsmily.petapi.utils.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (AdminUser)表控制层
 *
 * @author SongSmily
 * @since 2019-12-27 18:34:54
 */
@RestController
@RequestMapping("admin/adminUser")
public class AdminUserController {
    /**
     * 服务对象
     */
    @Resource
    private AdminUserService adminUserService;

    @RequiresPermissions("admin-all")
    @RequestMapping("doUpdate")
    @MyLog(value = "全局操作--修改账号信息")
    public Result doUpdate(@RequestBody AdminUser adminUser){
        boolean res = adminUserService.doUpdate(adminUser);
        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }

    /**
     * 重置密码
     * @param password
     * @return
     */
    @MyLog(value = "全局操作--修改密码")
    @RequiresPermissions("admin-all")
    @RequestMapping("resetPassword")
    public Result resetPassword(String password) {
        boolean res = adminUserService.resetPassword(password);

        return new Result( res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }


    @RequiresPermissions("admin-all")
    @RequestMapping("returnUserInfo")
    public Result returnUserInfo(){
        return adminUserService.returnAdminUserInfo();
    }

    @RequestMapping("/logOut")
    @MyLog(value = "全局操作--登出系统")
    public Result logOut(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new Result(ResultEnum.SUCCESS);
    }
}