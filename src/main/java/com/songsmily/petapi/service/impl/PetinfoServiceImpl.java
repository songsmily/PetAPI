package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.PetCheckfalseDao;
import com.songsmily.petapi.dao.PetinfoDao;
import com.songsmily.petapi.dao.PettypeDao;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCheckfalse;
import com.songsmily.petapi.entity.Petinfo;
import com.songsmily.petapi.entity.Pettype;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.PetinfoService;
import com.songsmily.petapi.utils.BASE64DecodedMultipartFile;
import com.songsmily.petapi.utils.Image2Base64;
import com.songsmily.petapi.utils.OssUtil;
import com.songsmily.petapi.utils.ShiroUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 宠物信息表(Petinfo)表服务实现类
 *
 * @author SongSmily
 * @since 2019-12-20 17:39:00
 */
@Service("petinfoService")
public class PetinfoServiceImpl extends ServiceImpl<PetinfoDao, Petinfo> implements PetinfoService {
    @Resource
    OssUtil ossUtil;
    @Resource
    PetinfoDao petinfoDao;
    @Resource
    PettypeDao pettypeDao;
    @Resource
    PetCheckfalseDao petCheckfalseDao;

    /**
     * 上传宠物信息
     * @param petinfo
     * @return
     */
    @Override
    public Result doUpload(Petinfo petinfo) {
        User user = ShiroUtil.getUser(new User());
        //上传图片
        MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(petinfo.getPetImageUrl());
        String res = ossUtil.uploadImg2Oss(multipartFile);
        petinfo.setPetImageUrl(res);
        petinfo.setGmtCreate(System.currentTimeMillis());
        petinfo.setGmtModified(petinfo.getGmtCreate());
        petinfo.setHosteId(user.getId());
        QueryWrapper<Pettype> pettypeQueryWrapper = new QueryWrapper<>();
        pettypeQueryWrapper.eq("pet_classify_three",petinfo.getPetType());
        Pettype pettype  = pettypeDao.selectOne(pettypeQueryWrapper);
        petinfo.setPetTypeId(pettype.getPetTypeId());
        petinfo.setPetNo(UUID.randomUUID().toString());

        int result = petinfoDao.insert(petinfo);
        if (result == 1){
            return new Result(CodeMsg.SUCCESS);
        }else{
            return new Result(CodeMsg.SERVERERROR);
        }

    }

    /**
     * 返回用户下所有宠物信息
     */
    @Override
    public Result selectAllPetInfos() {
        User user = ShiroUtil.getUser(new User());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("hoste_id",user.getId());
        wrapper.orderByDesc("pet_status");
        return new Result(petinfoDao.selectList(wrapper));
    }

    @Override
    public Result selectById(String petId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("pet_id",petId);
        Petinfo petinfo = petinfoDao.selectOne(wrapper);

        petinfo.setPetImageUrl(Image2Base64.image2Base64(petinfo.getPetImageUrl()));
        Map<String,Object> map = new HashMap<>();
        map.put("petInfo",petinfo);
        if (petinfo.getPetStatus() == -1){//判断是否为审核失败
            QueryWrapper wrapper1 = new QueryWrapper();
            wrapper1.eq("pet_id",petId);
            PetCheckfalse petCheckfalse = petCheckfalseDao.selectOne(wrapper1);
            map.put("petCheckfalse",petCheckfalse);
        }
        return new Result(map);
    }

    @Override
    public Result doReUpload(Petinfo petinfo) {
        petinfo.setPetStatus(0);
        petinfo.setGmtModified(System.currentTimeMillis());
        Petinfo petinfoOld = petinfoDao.selectById(petinfo.getPetId());
        String oldImageBase64 =  Image2Base64.image2Base64(petinfoOld.getPetImageUrl());
        if (!oldImageBase64.equals(petinfo.getPetImageUrl())){
            //上传图片
            MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(petinfo.getPetImageUrl());
            String res = ossUtil.uploadImg2Oss(multipartFile);
            petinfo.setPetImageUrl(res);
        }else{
            petinfo.setPetImageUrl(petinfoOld.getPetImageUrl());
        }
        if (petinfoDao.updateById(petinfo) != 0){
            String fileName = petinfoOld.getPetImageUrl().substring(petinfoOld.getPetImageUrl().lastIndexOf("/") + 1);
            List<String> list = new ArrayList<>();
            list.add(fileName);
            ossUtil.deleteFile20SS(list);
            return new Result(ResultEnum.SUCCESS);
        }else{
            return new Result(ResultEnum.ERROR);
        }
    }


}