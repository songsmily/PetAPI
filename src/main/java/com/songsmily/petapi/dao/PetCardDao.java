package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.PetCard;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * (PetCard)表数据库访问层
 *
 * @author SongSmily
 * @since 2020-03-20 09:00:47
 */
public interface PetCardDao extends BaseMapper<PetCard> {
    @Update("update pet_card set is_cancel=1 where pet_id=#{petId}")
    int cancelUpdateCard(@Param("petId") Integer petId);
}