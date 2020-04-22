package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Pettype;
import com.songsmily.petapi.service.PettypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Pettype)表控制层
 *
 * @author SongSmily
 * @since 2019-12-20 19:30:28
 */
@RestController
@RequestMapping("pettype")
public class PettypeController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private PettypeService pettypeService;

    /**
     * 查询宠物分类数据
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/getPetTypeArray")
    public Result getPetTypeArray() throws InterruptedException {
        Result petTypeArray = pettypeService.getPetTypeArray();
        return petTypeArray;
    }
    /**
     * 查询宠物分类数据 手机端
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/getPetTypeArrayMobile")
    public Result getPetTypeArrayMobile() throws InterruptedException {
        Result petTypeArray = pettypeService.getPetTypeArrayMobile();
        return petTypeArray;
    }
    /**
     * 根据宠物分类信息查询详细信息
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/getPetTypeByPetTypeId")
    public Result getPetTypeById(Integer petTypeId){
        return pettypeService.getPetTypeById(petTypeId);
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param pettype 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Pettype> page, Pettype pettype) {
        return success(this.pettypeService.page(page, new QueryWrapper<>(pettype)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.pettypeService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param pettype 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Pettype pettype) {
        return success(this.pettypeService.save(pettype));
    }

    /**
     * 修改数据
     *
     * @param pettype 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Pettype pettype) {
        return success(this.pettypeService.updateById(pettype));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.pettypeService.removeByIds(idList));
    }
}