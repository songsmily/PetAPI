package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.SysPetNotice;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.websocket.server.ServerEndpoint;
import java.util.List;

/**
 * (SysPetNotice)表数据库访问层
 *
 * @author SongSmily
 * @since 2020-03-18 08:25:49
 */
public interface SysPetNoticeDao extends BaseMapper<SysPetNotice> {

    @Select("Select  * from sys_pet_notice where notice_status!=-1 and user_id=#{userId} order by gmt_create desc limit #{topNumber} ")
    List<SysPetNotice> getMessage(Integer topNumber,Integer userId);

    @Update("Update sys_pet_notice set  notice_status=1 where notice_status=0 and user_id=#{userId}")
    Integer allHaveRead(Integer userId);

}