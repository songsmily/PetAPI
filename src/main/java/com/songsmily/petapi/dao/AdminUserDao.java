package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.AdminUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (AdminUser)表数据库访问层
 *
 * @author SongSmily
 * @since 2019-12-27 18:34:54
 */
public interface AdminUserDao extends BaseMapper<AdminUser> {
    /**
     * 查询管理员用户列表
     * @param selectMap
     * @return
     */
    List<AdminUser> selectAdminList(@Param("params") Map<String, Object> selectMap);

    List<AdminUser> selectUserNameList(Integer accountType);
}