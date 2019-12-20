package com.songsmily.petapi.controller;



import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dao.PlotDao;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Build;
import com.songsmily.petapi.entity.Plot;
import com.songsmily.petapi.service.PlotService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小区名称表(Plot)表控制层
 *
 * @author SongSmily
 * @since 2019-12-18 19:19:41
 */
@RestController
@RequestMapping("plot")
public class PlotController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    PlotService plotService;

    /**
     * 返回社区内小区和楼栋的级联数据
     * @return
     */
    @RequiresPermissions("user-all")
    @RequestMapping("getPlotAndBuild")
    public Result getPlotAndBuild(){
        return plotService.getPlotAndBuild();
    }

    /**
     * 返回用户所对应的小区ID和楼栋ID
     */
    @RequestMapping("/getUserLocationArray")
    @RequiresPermissions("user-all")
    public Result getUserLocationArray(String build_name){
        return plotService.getUserLocationArray(build_name);
    }
    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param plot 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Plot> page, Plot plot) {

        return success(this.plotService.page(page, new QueryWrapper<>(plot)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.plotService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param plot 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Plot plot) {
        return success(this.plotService.save(plot));
    }

    /**
     * 修改数据
     *
     * @param plot 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Plot plot) {
        return success(this.plotService.updateById(plot));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.plotService.removeByIds(idList));
    }
}