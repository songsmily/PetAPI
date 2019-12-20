package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.BuildDao;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Build;
import com.songsmily.petapi.service.BuildService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 楼栋名称表(Build)表服务实现类
 *
 * @author SongSmily
 * @since 2019-12-18 19:19:41
 */
@Service("buildService")
public class BuildServiceImpl extends ServiceImpl<BuildDao, Build> implements BuildService {
    @Resource
    BuildDao buildDao;
    /**
     * 返回用户所对应的小区ID和楼栋ID
     */
    @Override
    public Result getUserLocationArray(String build_name){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("build_name",build_name);
        return new Result(buildDao.selectOne(wrapper));
    }
}