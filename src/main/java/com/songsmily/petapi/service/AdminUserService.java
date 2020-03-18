package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.AdminUser;

/**
 * (AdminUser)表服务接口
 *
 * @author SongSmily
 * @since 2019-12-27 18:34:54
 */
public interface AdminUserService extends IService<AdminUser> {

    Result doLogin(AdminUser adminUser);

    Result returnAdminUserInfo();
}