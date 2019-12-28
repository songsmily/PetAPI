package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.PetinfoDao;
import com.songsmily.petapi.dao.PettypeDao;
import com.songsmily.petapi.dto.CodeMsg;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Petinfo;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.PetinfoService;
import com.songsmily.petapi.utils.BASE64DecodedMultipartFile;
import com.songsmily.petapi.utils.OssUtil;
import com.songsmily.petapi.utils.ShiroUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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

    /**
     * 上传宠物信息w
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
        Result result = new Result(petinfoDao.selectList(wrapper));
        return result;
    }

    @Override
    public Result selectById(String petId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("pet_id",petId);

        Result result = new Result(petinfoDao.selectOne(wrapper));
        return result;
    }
}