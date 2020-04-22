package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.UserService;
import com.songsmily.petapi.utils.Image2Base64;
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
     * 用户服务对象
     */
    @Autowired
    private UserService userService;


    /**
     * 用户首次登陆系统 完善个人信息
     * @param user
     * @return
     */
    @RequestMapping("/completeUserInfo")
    @RequiresPermissions("user-all")
    public Result completeUserInfo(@RequestBody User user){
        boolean res  = userService.completeUserInfo(user);

        return new Result(res ? ResultEnum.SUCCESS : ResultEnum.ERROR);
    }

    /**
     * 检测是否存在相同的用户名
     * @param name 用户填写的用户名
     * @return 返回 Result封装对象
     */
    @RequestMapping("/isRepeatName")
    @RequiresPermissions("user-all")
    public Result isRepeatNickName(String name){
        //调用Service
        Boolean isrepeat =  userService.isRepeatNickName(name);
        return new Result(isrepeat);
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
     * 修改数据 移动端
     * @param user 实体对象
     * @return 修改结果
     */
    @PostMapping("/doUpdateMobile")
    @RequiresPermissions("user-all")
    public Result updateMobile(@RequestBody User user) {
        String oldAvatar = ShiroUtil.getUser(new User()).getAvatarUrl();
        String oldBase64 = Image2Base64.image2Base64(oldAvatar);
        if (oldBase64.equals(user.getAvatarUrl())) {
            user.setAvatarUrl(oldAvatar);
        }

        return userService.doUpdate(user);
    }

    /**
     * 返回登录用户信息
     * @return 返回User信息
     */
    @RequiresPermissions(value = "user-all")
    @GetMapping("/returnUserInfo")
    public Result returnUserInfo(){
        //调用service
        return userService.returnUserInfo();
    }

    /**
     * 重置用户密码
     * @param password 用户重置的密码
     * @return 返回是否成功 修改
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/resetPassword")
    public Result resetPassword(String password){
        //调用service
        return userService.resetPassword(password);
    }

    //用户登录
    @RequestMapping(value="/login")
    public String login(String username,String password) {

        return userService.doLogin(username,password);
    }
    //退出登录
    @RequestMapping("/logOut")
    public Result logOut(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new Result(ResultEnum.SUCCESS);
    }

    /**
     * 将用户头像转成base64
     * @param imgUrl
     * @return
     */
    @RequestMapping("/getImgBase64")
    @RequiresPermissions("user-all")
    public Result getImgBase64(String imgUrl){
        String base64 = userService.getImgBase64(imgUrl);
        Result result = new Result();
        result.setData(base64);
        return result;
    }
}