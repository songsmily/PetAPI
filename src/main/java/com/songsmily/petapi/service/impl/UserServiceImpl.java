package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.UserDao;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.UserService;
import com.songsmily.petapi.utils.BASE64DecodedMultipartFile;
import com.songsmily.petapi.utils.CommonUtils;
import com.songsmily.petapi.utils.OssUtil;
import com.songsmily.petapi.utils.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (User)表服务实现类
 *
 * @author SongSmily
 * @since 2019-12-06 14:29:08
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Resource
    UserDao userDao;
    @Resource
    OssUtil ossUtil;
    @Override
    public Result returnUserInfo() {
        User user  = (User) ShiroUtil.getUser(new User());
        System.err.println("----" + user);
        Map<String,Object> data = new HashMap<>();
        data.put("accountId",user.getAccountId());
        data.put("accountType",user.getAccountType());
        data.put("id",user.getId());
        data.put("avatarUrl",user.getAvatarUrl());
        data.put("name",user.getName());
        data.put("bio",user.getBio());
        data.put("realName",user.getRealName());
        data.put("location",user.getLocation());
        data.put("address",user.getAddr());
        data.put("email",user.getEmail());
        data.put("phone",user.getPhone());
        System.err.println( "returnUserInfo ====" + data);
        return new Result(data);
    }

    @Override
    public String doLogin(String username, String password) {
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

    @Override
    public Boolean isRepeatNickName(String name) {
        User user = ShiroUtil.getUser(new User());
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("name",name);
        wrapper.ne("id",user.getId());
        return userDao.selectOne(wrapper) == null;
    }

    @Override
    public Result doUpdate(User user) {
        User oldUser = ShiroUtil.getUser(new User());
        System.err.println(user.getAvatarUrl());
        System.err.println(oldUser.getAvatarUrl());
        System.err.println(!user.getAvatarUrl().equals(oldUser.getAvatarUrl()));
        if (!user.getAvatarUrl().equals(oldUser.getAvatarUrl())){
            MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(user.getAvatarUrl());
            String res = ossUtil.uploadImg2Oss(multipartFile);
            List<String> filename = new ArrayList<>();
            filename.add(oldUser.getAvatarUrl().substring(oldUser.getAvatarUrl().lastIndexOf("images/")));
            ossUtil.deleteFile20SS(filename);
            oldUser.setAvatarUrl(res);
        }
        oldUser.setName(user.getName());
        oldUser.setRealName(user.getRealName());
        oldUser.setEmail(user.getEmail());
        oldUser.setLocation(user.getLocation());
        oldUser.setAddr(user.getAddr());
        oldUser.setBio(user.getBio());
        oldUser.setGmtModified(System.currentTimeMillis());
        ShiroUtil.setUser(oldUser);
        userDao.updateById(oldUser);
        return new Result(ResultEnum.SUCCESS);

    }

    @Override
    public Result resetPassword(String password) {
        User oldUser =  ShiroUtil.getUser(new User());
        User user = new User();
        user.setPassword(CommonUtils.setPassword(password));
        user.setId(oldUser.getId());
        if (userDao.updateById(user) == 1){
            oldUser.setPassword(user.getPassword());
            ShiroUtil.setUser(oldUser);
            return new Result(ResultEnum.SUCCESS);

        }else{
            return new Result(ResultEnum.ERROR);

        }
    }


}