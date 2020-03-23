package com.songsmily.petapi.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.entity.PetVac;
import com.songsmily.petapi.service.PetVacService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (PetVac)表控制层
 *
 * @author SongSmily
 * @since 2020-03-20 16:13:22
 */
@RestController
@RequestMapping("admin/petVac")
public class AdminPetVacController {
    /**
     * 服务对象
     */
    @Resource
    private PetVacService petVacService;
    


}