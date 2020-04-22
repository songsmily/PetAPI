package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.BlCommentSec;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 评论表(BlCommentSec)表数据库访问层
 *
 * @author SongSmily
 * @since 2020-04-08 10:36:33
 */
public interface BlCommentSecDao extends BaseMapper<BlCommentSec> {

    List<BlCommentSec> selectCommentSecs(@Param("params") Map<String, Object> selectMap);
}