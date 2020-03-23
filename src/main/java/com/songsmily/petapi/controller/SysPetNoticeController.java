package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.SysPetNotice;
import com.songsmily.petapi.service.SysPetNoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (SysPetNotice)表控制层
 *
 * @author SongSmily
 * @since 2020-03-18 08:25:49
 */
@RestController
@RequestMapping("sysPetNotice")
public class SysPetNoticeController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SysPetNoticeService sysPetNoticeService;


    @RequiresPermissions("user-all")
    @RequestMapping("/getMessage")
    public Result getMessage(Integer topNumber){
        return sysPetNoticeService.getMessage(topNumber);
    }

    @RequiresPermissions("user-all")
    @RequestMapping("/haveRead")
    public Result haveRead(Integer id){
        return sysPetNoticeService.haveRead(id);
    }
    @RequiresPermissions("user-all")
    @RequestMapping("/allHaveRead")
    public Result allHaveRead(){
        return sysPetNoticeService.allHaveRead();
    }

    @RequiresPermissions("user-all")
    @RequestMapping("/deleteNotice")
    public Result deleteNotice(Integer id){
        return sysPetNoticeService.deleteNotice(id);
    }
    @RequiresPermissions("user-all")
    @RequestMapping("/deleteAllNotice")
    public Result deleteAllNotice(){
        return sysPetNoticeService.deleteAllNotice();
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param sysPetNotice 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SysPetNotice> page, SysPetNotice sysPetNotice) {
        return success(this.sysPetNoticeService.page(page, new QueryWrapper<>(sysPetNotice)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysPetNoticeService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysPetNotice 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysPetNotice sysPetNotice) {
        return success(this.sysPetNoticeService.save(sysPetNotice));
    }

    /**
     * 修改数据
     *
     * @param sysPetNotice 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysPetNotice sysPetNotice) {
        return success(this.sysPetNoticeService.updateById(sysPetNotice));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysPetNoticeService.removeByIds(idList));
    }
}