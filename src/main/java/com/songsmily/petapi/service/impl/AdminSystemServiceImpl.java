package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.songsmily.petapi.dao.AdminUserDao;
import com.songsmily.petapi.dao.LogDao;
import com.songsmily.petapi.dao.UserDao;
import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.dto.SelectPageParams;
import com.songsmily.petapi.entity.AdminUser;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.AdminSystemService;
import com.songsmily.petapi.utils.CommonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("AdminSystemService")
public class AdminSystemServiceImpl implements AdminSystemService {
    @Resource
    UserDao userDao;
    @Resource
    AdminUserDao adminUserDao;
    @Resource
    LogDao logDao;

    /**
     * 查询用户列表
     * @param params
     * @return
     */
    @Override
    public BasePage getUserList(SelectPageParams params) {
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("begin", (params.getCurrentPage() - 1) * params.getPageSize());
        selectMap.put("end",params.getPageSize());

//        QueryWrapper wrapper = new QueryWrapper();
        if (params.getAreaFilter() != null) {
            selectMap.put("location", params.getAreaFilter());
//            wrapper.eq("location",params.getAreaFilter());
        }
        List<User> userList = userDao.selectUserList(selectMap);

        int total = userDao.selectCount(null);
        BasePage basePage = new BasePage();
        basePage.setRecords(userList);
        basePage.setTotal(total);
//        Page page  =  new Page(params.getCurrentPage(),params.getPageSize());
//        IPage iPage = userDao.selectPage(page,wrapper);

        return basePage;
    }

    /**
     * 查询管理员列表
     * @param params
     * @return
     */
    @Override
    public BasePage getAdminList(SelectPageParams params) {
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("begin", (params.getCurrentPage() - 1) * params.getPageSize());
        selectMap.put("end",params.getPageSize());
        selectMap.put("accountType",params.getAccountType());
        List<AdminUser> list = adminUserDao.selectAdminList(selectMap);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("account_type",params.getAccountType());
        Integer total = adminUserDao.selectCount(wrapper);
        BasePage page = new BasePage();
        page.setRecords(list);
        page.setTotal(total);

        return page;
    }

    /**
     * 禁用管理员账号
     * @param id
     * @return
     */
    @Override
    public boolean disableAdmin(String id) {
        AdminUser adminUser = adminUserDao.selectById(id);
        //设置禁用状态
        adminUser.setStatus(2);
        int i = adminUserDao.updateById(adminUser);
        return i > 0;
    }

    /**
     * 启用管理员账号
     * @param id
     * @return
     */
    @Override
    public boolean enableAdmin(String id) {
        AdminUser adminUser = adminUserDao.selectById(id);
        //设置启用状态
        adminUser.setStatus(1);
        int i = adminUserDao.updateById(adminUser);
        return i > 0;
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @Override
    public boolean deleteAdmin(String id) {
        int i = adminUserDao.deleteById(id);

        return i > 0;
    }

    /**
     * 核验用户名是否重复
     * @param username
     * @return
     */
    @Override
    public boolean checkUsername(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        AdminUser adminUser = adminUserDao.selectOne(wrapper);

        return  null == adminUser;
    }

    /**
     * 添加管理员
     * @param adminUser
     * @return
     */
    @Override
    public boolean addAdmin(AdminUser adminUser) {
        adminUser.setStatus(1);
        adminUser.setPassword(CommonUtils.setPassword(adminUser.getUsername()));
        int insert = adminUserDao.insert(adminUser);
        return insert > 0;
    }

    /**
     * 根据账户类型获取用户名和ID
     * @param accountType
     * @return
     */
    @Override
    public List<Map> getUserNameList(Integer accountType) {
        List<AdminUser> list = adminUserDao.selectUserNameList(accountType);
        List<Map> result = new ArrayList<>();
        Map<String, Object> map;

        for (AdminUser user: list) {
            map = new HashMap<>();
            map.put("value", user.getUsername());
            map.put("adminUser",user);

            result.add(map);
        }

        return result;
    }

    /**
     * 查询系统总览  包括用户信息和日志信息总数
     * @return
     */
    @Override
    public Map<String, Object> getSystemAllInfo() {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper wrapper;
        //查询社区用户信息
        wrapper = new QueryWrapper();
        wrapper.eq("account_type", 2);
        Integer qqUserTotal = userDao.selectCount(wrapper);

        wrapper = new QueryWrapper();
        wrapper.eq("account_type",1);
        Integer githubUserTotal = userDao.selectCount(wrapper);

        wrapper = new QueryWrapper();
        wrapper.eq("account_type",0);
        Integer registUserTotal = userDao.selectCount(wrapper);

        //查询管理员信息
        wrapper = new QueryWrapper();
        wrapper.eq("account_type", 1);
        Integer petAdminTotal = adminUserDao.selectCount(wrapper);
        wrapper.eq("status",2);
        Integer petAdminDisable = adminUserDao.selectCount(wrapper);

        wrapper = new QueryWrapper();
        wrapper.eq("account_type", 2);
        Integer communityAdminTotal = adminUserDao.selectCount(wrapper);

        wrapper.eq("status",2);
        Integer communityAdminDisable = adminUserDao.selectCount(wrapper);

        //查询日志总数
        Integer logCount = logDao.selectCount(null);

        map.put("qqUserTotal",qqUserTotal);
        map.put("githubUserTotal",githubUserTotal);
        map.put("registUserTotal",registUserTotal);
        map.put("petAdminTotal",petAdminTotal);

        map.put("petAdminDisable",petAdminDisable);
        map.put("communityAdminTotal",communityAdminTotal);
        map.put("communityAdminDisable",communityAdminDisable);
        map.put("logCount",logCount);
        return map;
    }


}
