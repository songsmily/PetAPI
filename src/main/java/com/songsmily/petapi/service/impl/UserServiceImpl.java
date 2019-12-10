package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.UserDao;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author SongSmily
 * @since 2019-12-06 14:29:08
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}