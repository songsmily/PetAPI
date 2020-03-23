package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author SongSmily
 * @since 2019-12-06 14:29:08
 */
public interface UserDao extends BaseMapper<User> {
    @Select("select id from user where location=#{area}")
    List<String> selectUserIdByArea(String area);

}