package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.PetImmunityDao;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetImmunity;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.PetImmunityService;
import com.songsmily.petapi.utils.BASE64DecodedMultipartFile;
import com.songsmily.petapi.utils.OssUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * (PetImmunity)表服务实现类
 *
 * @author SongSmily
 * @since 2020-03-20 09:00:20
 */
@Service("petImmunityService")
public class PetImmunityServiceImpl extends ServiceImpl<PetImmunityDao, PetImmunity> implements PetImmunityService {
    @Resource
    OssUtil ossUtil;
    @Resource
    PetImmunityDao petImmunityDao;
    @Override
    public Result insertImmnuity(PetImmunity petImmunity) {
        MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(petImmunity.getImmunityImageUrl());
        try {
            String url = ossUtil.uploadImg2Oss(multipartFile);
            if (url.equals("上传失败")){
                throw new Exception();
            }
            petImmunity.setImmunityImageUrl(url);
            petImmunity.setGmtCreate(System.currentTimeMillis());
            petImmunity.setGmtModified(petImmunity.getGmtCreate());
            petImmunity.setImmunityStatus(0);
            if (petImmunityDao.insert(petImmunity) > 0){
                return new Result(ResultEnum.SUCCESS);
            }
            throw new Exception();
        }catch (Exception e){
            return new Result(ResultEnum.ERROR);
        }
    }
}