package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.UserService;
import com.songsmily.petapi.utils.ShiroUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (User)表控制层
 *
 * @author SongSmily
 * @since 2019-12-06 14:29:08
 */
@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    @RequestMapping("/isRepeatName")
    @RequiresPermissions("user-all")
    public Result isRepeatNickName(String name){
        Boolean isrepeat =  userService.isRepeatNickName(name);
        return new Result(isrepeat);
    }

    /**
     * 返回登录用户信息
     * @return
     */
    @RequiresPermissions(value = "user-all")
    @GetMapping("/returnUserInfo")
    public Result returnUserInfo(){
        return userService.returnUserInfo();
    }


    @RequiresPermissions("user-all")
    @RequestMapping("/resetPassword")
    public Result resetPassword(String password){
        return userService.resetPassword(password);
    }

    //用户登录
    @RequestMapping(value="/login")
    public String login(String username,String password) {

        return userService.doLogin(username,password);
    }
    //退出登录
    @RequestMapping("/logOut")
    public String logOut(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "success";
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param user 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<User> page, User user) {
        return success(this.userService.page(page, new QueryWrapper<>(user)));
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.userService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param user 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody User user) {
        return success(this.userService.save(user));
    }

    /**
     * 修改数据
     * @param user 实体对象
     * @return 修改结果
     */
    @PostMapping("/doUpdate")
    @RequiresPermissions("user-all")
    public Result update(@RequestBody User user) {
        return userService.doUpdate(user);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.userService.removeByIds(idList));
    }
}