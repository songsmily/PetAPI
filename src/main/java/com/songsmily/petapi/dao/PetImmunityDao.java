package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.PetImmunity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (PetImmunity)表数据库访问层
 *
 * @author SongSmily
 * @since 2020-03-20 09:00:20
 */
public interface PetImmunityDao extends BaseMapper<PetImmunity> {
    @Update("update pet_immunity set is_cancel=1 where pet_id=#{petId}")
    int cancelUpdateImmunity(@Param("petId") Integer petId);
}