package com.songsmily.petapi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.songsmily.petapi.aspect.MyLog;
import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.dto.SelectPageParams;
import com.songsmily.petapi.entity.AdminUser;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.AdminSystemService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/system")
public class AdminSystemController {

    @Resource
    AdminSystemService adminSystemService;


    /**
     * 查询系统总览  包括用户信息和日志信息总数
     *
     * @return
     */
    @RequestMapping("getOverView")
    @RequiresRoles("sys-admin")
    public Result getSystemAllInfo() {
        Map<String, Object> map  = adminSystemService.getSystemAllInfo();
        return new Result(map);
    }
    /**
     * 查询管理员用户名列表
     * @param accountType
     * @return
     */
    @RequestMapping("getUserNameList")
    @RequiresRoles("sys-admin")
    public Result getAllAdminList(Integer accountType) {
        List<Map> list = adminSystemService.getUserNameList(accountType);
        return new Result(list);
    }

    /**
     * 添加管理员
     * @param adminUser
     * @return
     */
    @MyLog(value = "系统管理--添加管理员")
    @RequestMapping("addAdmin")
    @RequiresRoles("sys-admin")
    public Result addAdmin(@RequestBody AdminUser adminUser) {
        boolean res = adminSystemService.addAdmin(adminUser);
        return new Result(res ?  ResultEnum.SUCCESS : ResultEnum.ERROR);

    }

    /**
     * 核验用户名是否重复
     * @param username
     * @return
     */
    @RequestMapping("checkUsername")
    @RequiresRoles("sys-admin")
    public Result checkUsername(String username) {
        boolean res = adminSystemService.checkUsername(username);

        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }
    /**
     * 删除管理员
     * @param id
     * @return
     */
    @MyLog(value = "系统管理--删除管理员")
    @RequestMapping("deleteAdmin")
    @RequiresRoles("sys-admin")
    public Result deleteAdmin(String id) {
        boolean res = adminSystemService.deleteAdmin(id);

        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }
    /**
     * 启用管理员
     * @param id
     * @return
     */
    @MyLog(value = "系统管理--启用管理员")
    @RequestMapping("enableAdmin")
    @RequiresRoles("sys-admin")
    public Result enableAdmin(String id) {
        boolean res = adminSystemService.enableAdmin(id);

        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }
    /**
     * 禁用管理员
     * @param id
     * @return
     */
    @MyLog(value = "系统管理--禁用管理员")
    @RequestMapping("disableAdmin")
    @RequiresRoles("sys-admin")
    public Result disableAdmin(String id) {
        boolean res = adminSystemService.disableAdmin(id);

        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }

    /**
     * 查询管理员用户列表
     * @param params
     * @return
     */
    @MyLog(value = "系统管理--查询管理员用户列表")
    @RequestMapping("getAdminList")
    @RequiresRoles("sys-admin")
    public Result getAdminList(@RequestBody  SelectPageParams params){
        BasePage page = adminSystemService.getAdminList(params);
        return new Result(page);
    }
    /**
     * 查询用户列表
     * @param params
     * @return
     */
    @MyLog(value = "系统管理--查询社区用户列表")
    @RequestMapping("getUserList")
    @RequiresRoles("sys-admin")
    public Result getUserList(@RequestBody  SelectPageParams params){
        BasePage page = adminSystemService.getUserList(params);
        return new Result(page);
    }


}
