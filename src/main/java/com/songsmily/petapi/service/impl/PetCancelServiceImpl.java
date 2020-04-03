package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.PetCancelDao;
import com.songsmily.petapi.dao.PetCardDao;
import com.songsmily.petapi.dao.PetImmunityDao;
import com.songsmily.petapi.dao.PetinfoDao;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCancel;
import com.songsmily.petapi.entity.Petinfo;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.PetCancelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * (PetCancel)表服务实现类
 *
 * @author SongSmily
 * @since 2020-04-02 04:41:38
 */
@Service("petCancelService")
public class PetCancelServiceImpl extends ServiceImpl<PetCancelDao, PetCancel> implements PetCancelService {

    @Resource
    PetCancelDao petCancelDao;
    @Resource
    PetinfoDao petinfoDao;
    @Resource
    PetCardDao petCardDao;
    @Resource
    PetImmunityDao petImmunityDao;


    @Override
    @Transactional
    public Result doCancel(PetCancel petCancel) {
        //插入宠物注销详细信息
        petCancel.setGmtCreate(System.currentTimeMillis());
        petCancel.setGmtModified(petCancel.getGmtCreate());
        petCancel.setCancelStatus(1);
        petCancelDao.insert(petCancel);

        //修改宠物信息状态
        Petinfo petinfo = petinfoDao.selectById(petCancel.getPetId());
        petinfo.setIsCancel(1);
        petinfo.setGmtModified(System.currentTimeMillis());
        petinfoDao.updateById(petinfo);

        //修改宠物免疫证书信息状态
        petCardDao.cancelUpdateCard(petinfo.getPetId());

        //修改宠物免疫信息状态
        petImmunityDao.cancelUpdateImmunity(petinfo.getPetId());

        return new Result(ResultEnum.SUCCESS);
    }
}