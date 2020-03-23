package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.Petinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 宠物信息表(Petinfo)表数据库访问层
 *
 * @author SongSmily
 * @since 2019-12-20 17:39:00
 */
public interface PetinfoDao extends BaseMapper<Petinfo> {
    List<Petinfo> returnPetCardImmunityInfos(@Param("petId") Integer petId);
    List<Petinfo> returnUnCheckCardPetInfo(@Param("params") Map<String,Object> map);
    Integer returnUnCheckCardPetInfoCountByUserIDs(@Param("userIDs") List userIDs);
    List<Petinfo> returnUnCheckPetInfo(@Param("params") Map<String,Object> map);
}