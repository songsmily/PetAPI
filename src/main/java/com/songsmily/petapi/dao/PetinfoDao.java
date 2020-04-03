package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.PetCardImmunityInfo;
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
    /**
     *  宠物管理端-》宠物信息管理-》信息查询 查询 宠物以及宠物证书信息 可以根据UserIDs条件查询
     * @param map
     * @return
     */
    List<Petinfo> petManageSelectPetInfoStepOneReturnPetAndPetCardInfo(@Param("params") Map<String, Object> map);

    /**
     *
     * 宠物管理端-》宠物信息管理-》信息查询 查询 宠物以及宠物证书信息 可以根据UserIDs条件查询 总记录条数
     *
     * @param map
     * @return
     */
    Integer petManageSelectPetInfoStepOneReturnPetAndPetCardInfoCount(@Param("params") Map<String, Object> map);
    /**
     * 查询宠物、宠物免疫信息 组合对象
     * @param map
     * @return
     */


    List<Petinfo> returnPetCardImmunityInfos(@Param("params") Map<String, Object> map);

    /**
     * 查询宠物.免疫信息 单个对象
     * @param map
     * @return
     */
    List<PetCardImmunityInfo> returnPetCardImmunityInfosByUserIDs(@Param("params") Map<String,Object> map);
    List<Petinfo> returnUnCheckCardPetInfo(@Param("params") Map<String,Object> map);
    Integer returnUnCheckCardPetInfoCountByUserIDs(@Param("userIDs") List userIDs);
    List<Petinfo> returnUnCheckPetInfo(@Param("params") Map<String,Object> map);

    Integer returnPetCardImmunityInfosCountByUserIDs(@Param("params")Map<String, Object> map );
}