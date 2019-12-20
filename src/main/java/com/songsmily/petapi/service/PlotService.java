package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Plot;

import java.util.List;

/**
 * 小区名称表(Plot)表服务接口
 *
 * @author SongSmily
 * @since 2019-12-18 19:19:41
 */
public interface PlotService extends IService<Plot> {

    Result getUserLocationArray(String build_name);

    Result getPlotAndBuild();
}