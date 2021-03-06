package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.AdminUserDao;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.AdminUser;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.AdminUserService;
import com.songsmily.petapi.shiro.common.UserToken;
import com.songsmily.petapi.shiro.enums.LoginType;
import com.songsmily.petapi.utils.CommonUtils;
import com.songsmily.petapi.utils.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * (AdminUser)表服务实现类
 *
 * @author SongSmily
 * @since 2019-12-27 18:34:54
 */
@Service("adminUserService")
public class AdminUserServiceImpl extends ServiceImpl<AdminUserDao, AdminUser> implements AdminUserService {
    private static final String ADMINUSER_LOGIN_TYPE = LoginType.ADMINUSER.toString();
    @Resource
    AdminUserDao adminUserDao;

    @Override
    public Result doLogin(AdminUser adminUser) {
        String username = adminUser.getUsername();
        String password = CommonUtils.setPassword(adminUser.getPassword());

        QueryWrapper wrapper  = new QueryWrapper();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        AdminUser testUser = adminUserDao.selectOne(wrapper);

        if (null == testUser) {
            return new Result(ResultEnum.USERNAME_PASSWORD_ERROR);
        } else if (testUser.getStatus() != 1) {
            return new Result(ResultEnum.ACCOUNT_DISABLE_ERROR);
        }

        //判断管理员是否可以登录
//        if (user.getStatus() != 1){
//            throw new BaseException(ResultEnum.ACCOUNT_DISABLE_ERROR);
//        }
        try {
            UserToken userToken = new UserToken(username,password, ADMINUSER_LOGIN_TYPE);
            //1.获取subject
            Subject subject = SecurityUtils.getSubject();
            //2.调用subject进行登录
            subject.login(userToken);
            return returnAdminUserInfo();
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(ResultEnum.ERROR);
        }
    }
    @Override
    public Result returnAdminUserInfo(){
        AdminUser adminUser = ShiroUtil.getUser(new AdminUser());
        Map<String,Object> data = new HashMap<>();
        data.put("username",adminUser.getUsername());
        data.put("accountType",adminUser.getAccountType());
        data.put("phone",adminUser.getPhone());
        data.put("realName",adminUser.getRealName());
        data.put("status",adminUser.getStatus());
        return new Result(data);
    }

    /**
     * 重置密码
     * @param password
     * @return
     */
    @Override
    public boolean resetPassword(String password) {
        AdminUser adminUser = ShiroUtil.getUser(new AdminUser());
        adminUser.setPassword(CommonUtils.setPassword(password));
        int i = adminUserDao.updateById(adminUser);
        return i > 0;
    }

    /**
     * 修改信息
     * @param adminUser
     * @return
     */
    @Override
    public boolean doUpdate(AdminUser adminUser) {
        AdminUser oldUser = ShiroUtil.getUser(new AdminUser());
        oldUser.setRealName(adminUser.getRealName());
        oldUser.setPhone(adminUser.getPhone());
        int i = adminUserDao.updateById(oldUser);
        if (i > 0){
            ShiroUtil.setUser(oldUser);
            return true;
        }
        return false;
    }
}