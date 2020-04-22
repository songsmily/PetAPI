package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.PetImmunityDao;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetImmunity;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.PetImmunityService;
import com.songsmily.petapi.utils.BASE64DecodedMultipartFile;
import com.songsmily.petapi.utils.Image2Base64;
import com.songsmily.petapi.utils.OssUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public Result updateImmunity(PetImmunity petImmunity) {
        PetImmunity oldPetImmunity = petImmunityDao.selectById(petImmunity.getPetImmunityId());

        if (!oldPetImmunity.getImmunityImageUrl().equals(petImmunity.getImmunityImageUrl())){
            MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(petImmunity.getImmunityImageUrl());
            try {
                String url = ossUtil.uploadImg2Oss(multipartFile);
                if (url.equals("上传失败")){
                    throw new Exception();
                }
                petImmunity.setImmunityImageUrl(url);

                List<String> list = new ArrayList<>();
                list.add(oldPetImmunity.getImmunityImageUrl().substring(oldPetImmunity.getImmunityImageUrl().lastIndexOf("/") + 1));
                ossUtil.deleteFile20SS(list);
            }catch (Exception e){
                return new Result(ResultEnum.ERROR);

            }
        } else {
            petImmunity.setImmunityImageUrl(oldPetImmunity.getImmunityImageUrl());
        }
        petImmunity.setFalseRes("-1");
        petImmunity.setImmunityStatus(0);
        petImmunity.setGmtModified(System.currentTimeMillis());
        if (petImmunityDao.updateById(petImmunity) > 0){
            return new Result(ResultEnum.SUCCESS);
        }
        return new Result(ResultEnum.ERROR);

    }

    /**
     * 根据petId查询免疫信息
     * @param petId
     * @return
     */
    @Override
    public List<PetImmunity> getImmunityById(String petId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("pet_id", petId);
        List list = petImmunityDao.selectList(wrapper);

        return list;
    }

    @Override
    public PetImmunity getImmunityInfoByImmunityId(String immunityId) {
        PetImmunity petImmunity = petImmunityDao.selectById(immunityId);
//        petImmunity.setImmunityImageUrl(Image2Base64.image2Base64(petImmunity.getImmunityImageUrl()));


        return petImmunity;
    }

}