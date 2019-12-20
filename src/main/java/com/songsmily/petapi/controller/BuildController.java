package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Build;
import com.songsmily.petapi.service.BuildService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 楼栋名称表(Build)表控制层
 *
 * @author SongSmily
 * @since 2019-12-18 19:19:41
 */
@RestController
@RequestMapping("build")
public class BuildController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private BuildService buildService;

    /**
     * 返回用户所对应的小区ID和楼栋ID
     */
    @RequestMapping("/getUserLocationArray")
    @RequiresPermissions("user-all")
    public Result getUserLocationArray(String build_name){
        return buildService.getUserLocationArray(build_name);
    }
    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param build 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Build> page, Build build) {
        return success(this.buildService.page(page, new QueryWrapper<>(build)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.buildService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param build 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Build build) {
        return success(this.buildService.save(build));
    }

    /**
     * 修改数据
     *
     * @param build 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Build build) {
        return success(this.buildService.updateById(build));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.buildService.removeByIds(idList));
    }
}