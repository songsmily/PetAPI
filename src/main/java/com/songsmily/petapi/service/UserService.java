package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.User;

/**
 * (User)表服务接口
 *
 * @author SongSmily
 * @since 2019-12-06 14:29:08
 */
public interface UserService extends IService<User> {
    Result returnUserInfo();
    String doLogin(String username, String Password);

    Boolean isRepeatNickName(String name);

    Result doUpdate(User user);

    Result resetPassword(String password);

    boolean completeUserInfo(User user);

    String getImgBase64(String imgUrl);
}