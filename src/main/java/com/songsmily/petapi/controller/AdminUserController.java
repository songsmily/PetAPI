package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class AdminUserController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AdminUserService adminUserService;

    @RequiresPermissions("admin-all")
    @RequestMapping("returnUserInfo")
    public Result returnUserInfo(){
        return adminUserService.returnAdminUserInfo();
//        AdminUser adminUser = ShiroUtil.getUser(new AdminUser());
//        adminUser.setPassword(null);
    }


    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param adminUser 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<AdminUser> page, AdminUser adminUser) {
        return success(this.adminUserService.page(page, new QueryWrapper<>(adminUser)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.adminUserService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param adminUser 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody AdminUser adminUser) {
        return success(this.adminUserService.save(adminUser));
    }

    /**
     * 修改数据
     *
     * @param adminUser 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody AdminUser adminUser) {
        return success(this.adminUserService.updateById(adminUser));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.adminUserService.removeByIds(idList));
    }
    @RequestMapping("/logOut")
    public Result logOut(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new Result(ResultEnum.SUCCESS);
    }
}