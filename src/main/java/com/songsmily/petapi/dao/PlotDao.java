package com.songsmily.petapi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songsmily.petapi.entity.Plot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 小区名称表(Plot)表数据库访问层
 *
 * @author SongSmily
 * @since 2019-12-18 19:19:41
 */
public interface PlotDao extends BaseMapper<Plot> {
    List<Plot> queryPlotAndBuild();
    List<Plot> queryAll();
}