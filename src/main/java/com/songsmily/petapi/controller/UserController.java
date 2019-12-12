package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
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
    @Resource
    private UserService userService;

    /**
     * 返回登录用户信息
     * @return
     */
    @RequiresPermissions(value = "user-all")
    @GetMapping("/returnUserInfo")
    public Result returnUserInfo(){
        User user  = (User) SecurityUtils.getSubject().getPrincipal();
        System.err.println("-----User" + user.toString());
        Map<String,Object> data = new HashMap<>();
        data.put("accountId",user.getAccountId());
        data.put("accountType",user.getAccountType());
        data.put("id",user.getId());
        data.put("avatarUrl",user.getAvatarUrl());
        data.put("name",user.getName());
        data.put("bio",user.getBio());
        Result result = new Result(data);
        return result;
    }
    //用户登录
    @RequestMapping(value="/login")
    public String login(String username,String password) {
        //构造登录令牌
        try {

            /**
             * 密码加密：
             *     shiro提供的md5加密
             *     Md5Hash:
             *      参数一：加密的内容
             *              111111   --- abcd
             *      参数二：盐（加密的混淆字符串）（用户登录的用户名）
             *              111111+混淆字符串
             *      参数三：加密次数
             *
             */
            password = new Md5Hash(password,null,3).toString();

            UsernamePasswordToken upToken = new UsernamePasswordToken(username,password);
            //1.获取subject
            Subject subject = SecurityUtils.getSubject();

            //获取session
            String sid = (String) subject.getSession().getId();

            //2.调用subject进行登录
            subject.login(upToken);
            return "登录成功";
        }catch (Exception e) {
            return "用户名或密码错误";
        }
    }
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
     *
     * @param user 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody User user) {
        return success(this.userService.updateById(user));
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