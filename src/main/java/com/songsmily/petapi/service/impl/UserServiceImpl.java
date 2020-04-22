package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.UserDao;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.UserService;
import com.songsmily.petapi.utils.*;
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
    @Resource
    ContentReview contentReview;

    /**
     * 返回已登录用户信息
     * @return
     */
    @Override
    public Result returnUserInfo() {
        //调用ShiroUtil返回已登录用户信息
        User user  = (User) ShiroUtil.getUser(new User());

        //封装user信息
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

        //返回Result对象 封装data
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

    /**
     * 检测是否存在相同的用户名
     * @param name 用户名
     * @return
     */
    @Override
    public Boolean isRepeatNickName(String name) {
        //调用ShiroUtil获取到已登录的user对象
        User user = ShiroUtil.getUser(new User());
        QueryWrapper<User> wrapper = new QueryWrapper();

        //配置查询条件
        wrapper.eq("name",name);
        wrapper.ne("id",user.getId());

        //查询数据库 判断是否为空并返回
        return userDao.selectOne(wrapper) == null;
    }

    @Override
    public Result doUpdate(User user) {
        User oldUser = ShiroUtil.getUser(new User());
        //审核签名

        if (!user.getBio().equals(oldUser.getBio())) {
            contentReview.reviewBio(user.getBio());
        }

        if (!user.getAvatarUrl().equals(oldUser.getAvatarUrl())){
            MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(user.getAvatarUrl());
            //审核头像
            contentReview.reviewAvartar(multipartFile);
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

    /**
     * 重置用户密码
     * @param password 密码
     * @return
     */
    @Override
    public Result resetPassword(String password) {
        //获取已登录用户对象
        User oldUser =  ShiroUtil.getUser(new User());

        //封装用户对象 替换数据库中密码信息
        User user = new User();
        user.setPassword(CommonUtils.setPassword(password));
        user.setId(oldUser.getId());

        //替换用户数据
        if (userDao.updateById(user) == 1){
            oldUser.setPassword(user.getPassword());
            ShiroUtil.setUser(oldUser);
            return new Result(ResultEnum.SUCCESS);
        }else{
            return new Result(ResultEnum.ERROR);

        }
    }

    /**
     * 用户 首次登陆 完善个人信息
     * @param user
     * @return
     */
    @Override
    public boolean completeUserInfo(User user) {

        User oldUSer = ShiroUtil.getUser(new User());
        oldUSer.setLocation(user.getLocation());
        oldUSer.setAddr(user.getAddr());
        oldUSer.setEmail(user.getEmail());
        oldUSer.setRealName(user.getRealName());

        if (null != user.getPhone()) {
            oldUSer.setPhone(user.getPhone());
            oldUSer.setName(user.getName());
        }

        int i = userDao.updateById(oldUSer);
        if (i > 0){
            ShiroUtil.setUser(oldUSer);
        }

        return i > 0;
    }

    /**
     * 将用户头像转成base64
     * @param imgUrl
     * @return
     */
    @Override
    public String getImgBase64(String imgUrl) {
        return Image2Base64.image2Base64(imgUrl);
    }


}