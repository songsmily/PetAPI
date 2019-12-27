package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Petinfo;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.PetinfoService;
import com.songsmily.petapi.utils.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 宠物信息表(Petinfo)表控制层
 *
 * @author SongSmily
 * @since 2019-12-20 17:39:00
 */
@RestController
@RequestMapping("petinfo")
public class PetinfoController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private PetinfoService petinfoService;
    /**
     * 根据id查询宠物详细信息
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/getPetInfoById")
    public Result getPetInfoById(String petId) {
        return petinfoService.selectById(petId);
    }

    /**
     * 返回用户下所有宠物信息
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/returnPetInfos")
    public Result returnPetInfos(){
        return petinfoService.selectAllPetInfos();
    }
    /**
     * 上传宠物信息
     */
    @RequiresPermissions("user-all")
    @RequestMapping("/doUpload")
    public Result doUpload(@RequestBody Petinfo petinfo){
        return petinfoService.doUpload(petinfo);
    }
    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param petinfo 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Petinfo> page, Petinfo petinfo) {
        return success(this.petinfoService.page(page, new QueryWrapper<>(petinfo)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.petinfoService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param petinfo 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Petinfo petinfo) {
        return success(this.petinfoService.save(petinfo));
    }

    /**
     * 修改数据
     *
     * @param petinfo 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Petinfo petinfo) {
        return success(this.petinfoService.updateById(petinfo));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.petinfoService.removeByIds(idList));
    }
}