package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.PetVacDao;
import com.songsmily.petapi.dao.PetinfoDao;
import com.songsmily.petapi.dao.PettypeDao;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetVac;
import com.songsmily.petapi.entity.Petinfo;
import com.songsmily.petapi.entity.Pettype;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.PetVacService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PetVac)表服务实现类
 *
 * @author SongSmily
 * @since 2020-03-20 16:13:22
 */
@Service("petVacService")
public class PetVacServiceImpl extends ServiceImpl<PetVacDao, PetVac> implements PetVacService {
    @Resource
    PetinfoDao petinfoDao;
    @Resource
    PettypeDao pettypeDao;
    @Resource
    PetVacDao petVacDao;
    @Override
    public Result returnPetVac(Integer petId) {
        Petinfo petinfo = petinfoDao.selectById(petId);
        Pettype pettype = pettypeDao.selectById(petinfo.getPetTypeId());
        String classifyOne = pettype.getPetClassifyOne();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("pet_classify_one",classifyOne);

        List<PetVac> petVacs = petVacDao.selectList(wrapper);
        if (null != petVacs){
            return new Result(petVacs);
        }else{
            return new Result(ResultEnum.ERROR);
        }
    }
}