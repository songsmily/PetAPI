package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.Log;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 接口访问日志表(Log)表数据库访问层
 *
 * @author SongSmily
 * @since 2020-03-16 12:03:45
 */
public interface LogDao extends BaseMapper<Log> {
    List<Log> selectDisUserNameList();
}