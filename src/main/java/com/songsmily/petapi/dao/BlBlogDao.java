package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.BlBlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 博客表(BlBlog)表数据库访问层
 *
 * @author SongSmily
 * @since 2020-04-06 11:01:31
 */
public interface BlBlogDao extends BaseMapper<BlBlog> {

    List<BlBlog> selectBlogPageByType(@Param("params") Map<String, Object> selectMapper);

    BlBlog selectBlogById(String blogId);
}