package com.songsmily.petapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.PlotDao;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Build;
import com.songsmily.petapi.entity.Plot;
import com.songsmily.petapi.service.PlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小区名称表(Plot)表服务实现类
 *
 * @author SongSmily
 * @since 2019-12-18 19:19:41
 */
@Service("plotService")
public class PlotServiceImpl extends ServiceImpl<PlotDao, Plot> implements PlotService {
    @Resource
    PlotDao plotDao;
    /**
     * 返回用户所对应的小区ID和楼栋ID
     */
    @Override
    public Result getUserLocationArray(String build_name){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("build_name",build_name);
        return new Result(plotDao.selectOne(wrapper));
    }
    /**
     * 返回社区内小区和楼栋的级联数据
     * @return
     */
    @Override
    public Result getPlotAndBuild(){
        List<Plot> plots = plotDao.queryPlotAndBuild();
        List<Map<String,Object>> result = new ArrayList<>();
        for (Plot plot : plots){
            Map<String,Object> map = new HashMap<>();
            map.put("value",plot.getPlotId());
            map.put("label",plot.getPlotName());
            List<Map<String,Object>> childResult = new ArrayList<>();

            for (Build build: plot.getBuilds()) {
                Map<String,Object> childMap = new HashMap<>();

                childMap.put("label",build.getBuildName());
                childMap.put("value",build.getBuildId());
                childResult.add(childMap);
            }

            map.put("children",childResult);
            result.add(map);
        }

        return new Result(JSONObject.toJSON(result));
    }

}