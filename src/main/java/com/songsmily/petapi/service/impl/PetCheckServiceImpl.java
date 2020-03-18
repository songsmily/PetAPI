package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dao.PetCheckfalseDao;
import com.songsmily.petapi.dao.PetinfoDao;
import com.songsmily.petapi.dao.UserDao;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCheckfalse;
import com.songsmily.petapi.entity.Petinfo;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.PetCheckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class PetCheckServiceImpl  implements PetCheckService {
    @Resource
    PetinfoDao petinfoDao;
    @Resource
    UserDao userDao;
    @Resource
    PetCheckfalseDao petCheckfalseDao;
    @Override
    public Result selectUnCheckList(Integer currentPage, Integer pageSize) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("pet_status",0);
        Page page = new Page(currentPage,pageSize);

        IPage iPage = petinfoDao.selectPage(page,wrapper);

        return new Result(iPage);

    }

    @Override
    public Result getPetAndHostInfoById(Integer petId) {

        Petinfo petinfo = petinfoDao.selectById(petId);
        User user = userDao.selectById(petinfo.getHosteId());
        Map<String,Object> map = new HashMap<>();
        map.put("petInfo",petinfo);
        map.put("userInfo",user);

        return new Result(map);
    }

    @Override
    @Transactional
    public Result doCheckFalse(Integer petId, String falseRes) {
        PetCheckfalse petCheckfalse = new PetCheckfalse();
        petCheckfalse.setPetId(petId);
        petCheckfalse.setFalseRes(falseRes);
        petCheckfalse.setGmtCreate(System.currentTimeMillis());
        Petinfo petinfo = petinfoDao.selectById(petId);
        petinfo.setPetStatus(-1);//设置属性为-1，审核失败
        if (petCheckfalseDao.insert(petCheckfalse) != 0 && petinfoDao.updateById(petinfo) != 0) {
            return new Result(ResultEnum.SUCCESS);
        }else{
            return new Result(ResultEnum.ERROR);
        }
    }

    @Override
    public Result doCheckPass(Integer petId) {
        Petinfo petinfo = petinfoDao.selectById(petId);
        petinfo.setPetStatus(1);
        if(petinfoDao.updateById(petinfo) != 0){
             return new Result(ResultEnum.SUCCESS);

        }else{
            return new Result(ResultEnum.ERROR);
        }
    }
}
