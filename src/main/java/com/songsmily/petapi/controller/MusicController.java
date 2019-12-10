package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.entity.Music;
import com.songsmily.petapi.service.MusicService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Music)表控制层
 *
 * @author SongSmily
 * @since 2019-12-03 15:24:22
 */
@CrossOrigin
@RestController
@RequestMapping("music")
public class MusicController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private MusicService musicService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param music 查询实体
     * @return 所有数据
     */
    @GetMapping("/select")
    public R select(Page<Music> page, Music music) {
        return success(this.musicService.page(page, new QueryWrapper<>(music)));
    }

    /*
     *查询所有歌曲数据
     *
     */
    @RequiresPermissions(value = "users")
    @GetMapping("/selectAll")
    public R selectAll(){
        return success(this.musicService.list());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.musicService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param music 实体对象
     * @return 新增结果
     */
    @PostMapping("/insert")
    @ResponseBody
    public R insert(@RequestBody Music music) {
        System.err.println(music);
        return success(this.musicService.save(music));
    }

    @ResponseBody
    @RequestMapping("/test")
    public void test(Music music){
        System.err.println(music.toString());
    }

    /**
     * 修改数据
     *
     * @param music 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Music music) {
        return success(this.musicService.updateById(music));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.musicService.removeByIds(idList));
    }

}