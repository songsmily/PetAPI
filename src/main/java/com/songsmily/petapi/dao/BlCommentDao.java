package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.BlComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 评论表(BlComment)表数据库访问层
 *
 * @author SongSmily
 * @since 2020-04-07 15:44:18
 */
public interface BlCommentDao extends BaseMapper<BlComment> {

    List<BlComment> selectCommentAndSecondComment(String blogId);
    @Select("select comment_id from bl_comment where comment_blog=#{blogId} and deleted=0")
    List<String> selectCommentIdsByBlogId(String blogId);

    List<BlComment> selectComments(@Param("params") Map<String, Object> selectMap);

}