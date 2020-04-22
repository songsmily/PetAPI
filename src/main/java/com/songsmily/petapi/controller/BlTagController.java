package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.BlTag;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.exception.BaseException;
import com.songsmily.petapi.service.BlTagService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (BlTag)表控制层
 *
 * @author SongSmily
 * @since 2020-04-10 14:22:55
 */
@RestController
@RequestMapping("blTag")
public class BlTagController {
    /**
     * 服务对象
     */
    @Resource
    private BlTagService blTagService;

    /**
     * 获取热门标签
     * @return
     */
    @RequiresPermissions(("user-all"))
    @RequestMapping("getHotTags")
    public Result getHotTags() {
        List<BlTag> hotTags = blTagService.getHotTags();
        return new Result(hotTags);
    }

    /**
     * 获取所有帖子标签
     * @return
     */
    @RequiresPermissions(("user-all"))
    @RequestMapping("getTags")
    public Result getTags() {
        List<BlTag> tags = blTagService.getTags();
        return new Result(tags);
    }

    /**
     * 新增帖子标签
     * @param blTag
     * @return
     */
    @RequiresPermissions(("user-all"))
    @RequestMapping("addTag")
    public Result addTags(@RequestBody  BlTag blTag) {
        if (null == blTag.getTagName())
            throw new BaseException(ResultEnum.PARAMS_NULL);
        boolean res = blTagService.addTag(blTag);
        return new Result();
    }


}