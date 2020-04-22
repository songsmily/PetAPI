package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.PetCardDao;
import com.songsmily.petapi.dao.PetImmunityDao;
import com.songsmily.petapi.dao.PetinfoDao;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.PetCard;
import com.songsmily.petapi.entity.PetImmunity;
import com.songsmily.petapi.entity.Petinfo;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.PetCardService;
import com.songsmily.petapi.utils.BASE64DecodedMultipartFile;
import com.songsmily.petapi.utils.Image2Base64;
import com.songsmily.petapi.utils.OssUtil;
import com.songsmily.petapi.utils.ShiroUtil;
import org.assertj.core.error.uri.ShouldHaveUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (PetCard)表服务实现类
 *
 * @author SongSmily
 * @since 2020-03-20 09:00:47
 */
@Service("petCardService")
public class PetCardServiceImpl extends ServiceImpl<PetCardDao, PetCard> implements PetCardService {

    @Resource
    PetinfoDao petinfoDao;
    @Resource
    PetCardDao petCardDao;
    @Resource
    PetImmunityDao petImmunityDao;
    @Resource
    OssUtil ossUtil;

    @Override
    public Result returnPetCardImmunityInfos() {
        User user = ShiroUtil.getUser(new User());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("hoste_id", user.getId());
        wrapper.eq("is_cancel", 0);
        wrapper.ne("pet_status", -1);
        List<Petinfo> res = petinfoDao.selectList(wrapper);
        List<Petinfo> newRes = new ArrayList<>();
        if (null != res) {
            for (Petinfo petinfo :
                    res) {
                wrapper = new QueryWrapper();
                wrapper.eq("pet_id", petinfo.getPetId());
                PetCard petCard = petCardDao.selectOne(wrapper);
                if (null != petCard) {
                    wrapper = new QueryWrapper();
                    wrapper.eq("pet_card_id", petCard.getPetCardId());
                    List<PetImmunity> petImmunities = petImmunityDao.selectList(wrapper);
                    petCard.setPetImmunities(petImmunities);
                }
                petinfo.setPetCard(petCard);
                newRes.add(petinfo);
            }
        }

        return new Result(newRes);
    }

    @Override
    public Result insertPetCard(PetCard petCard) {
        MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(petCard.getCardImageUrl());
        try {
            String url = ossUtil.uploadImg2Oss(multipartFile);
            petCard.setCardImageUrl(url);
            petCard.setCardStatus(0);
            petCard.setGmtCreated(System.currentTimeMillis());
            petCard.setGmtModified(petCard.getGmtCreated());
            petCard.setImmunityCount(0);
            if (petCardDao.insert(petCard) == 1) {
                return new Result(ResultEnum.SUCCESS);
            }
            throw new Exception();
        } catch (Exception e) {
            return new Result(ResultEnum.ERROR);
        }

    }

    @Override
    public Result updatePetCard(PetCard petCard) {
        PetCard oldPetcard = petCardDao.selectById(petCard.getPetCardId());
        if (!oldPetcard.getCardImageUrl().equals(petCard.getCardImageUrl())) {
            MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(petCard.getCardImageUrl());
            try {
                String url = ossUtil.uploadImg2Oss(multipartFile);
                if (url.equals("上传失败")) {
                    throw new Exception();
                }
                List<String> list = new ArrayList<>();
                list.add(oldPetcard.getCardImageUrl().substring(oldPetcard.getCardImageUrl().lastIndexOf("/") + 1));
                ossUtil.deleteFile20SS(list);
                petCard.setCardImageUrl(url);
            } catch (Exception e) {
                return new Result(ResultEnum.ERROR);
            }
        }
        petCard.setCardStatus(0);
        petCard.setFalseRes("-1");
        if (petCardDao.updateById(petCard) > 0) {
            return new Result(ResultEnum.SUCCESS);
        } else {
            return new Result(ResultEnum.ERROR);
        }
    }

    @Override
    public Petinfo returnPetCardImmunityInfosByPetId(String petId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("pet_id", petId);
        Petinfo petinfo = petinfoDao.selectOne(wrapper);
        wrapper = new QueryWrapper();
        wrapper.eq("pet_id", petinfo.getPetId());
        PetCard petCard = petCardDao.selectOne(wrapper);
        if (null != petCard) {
//            petCard.setCardImageUrl(Image2Base64.image2Base64(petCard.getCardImageUrl()));

            wrapper = new QueryWrapper();
            wrapper.eq("pet_card_id", petCard.getPetCardId());
            List<PetImmunity> petImmunities = petImmunityDao.selectList(wrapper);
            petCard.setPetImmunities(petImmunities);
        }

        petinfo.setPetCard(petCard);
        petinfo.setPetCard(petCard);

        return petinfo;
    }

    @Override
    public Result updatePetCardMobile(PetCard petCard) {
        PetCard oldPetcard = petCardDao.selectById(petCard.getPetCardId());
        String oldImageBase64 = Image2Base64.image2Base64(oldPetcard.getCardImageUrl());

        if (!oldImageBase64.equals(petCard.getCardImageUrl())) {
            MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(petCard.getCardImageUrl());
            try {
                String url = ossUtil.uploadImg2Oss(multipartFile);
                if (url.equals("上传失败")) {
                    throw new Exception();
                }
                List<String> list = new ArrayList<>();
                list.add(oldPetcard.getCardImageUrl().substring(oldPetcard.getCardImageUrl().lastIndexOf("/") + 1));
                ossUtil.deleteFile20SS(list);
                petCard.setCardImageUrl(url);
            } catch (Exception e) {
                return new Result(ResultEnum.ERROR);
            }
        } else {

            petCard.setCardImageUrl(oldPetcard.getCardImageUrl());
        }
        petCard.setCardStatus(0);
        petCard.setFalseRes("-1");
        if (petCardDao.updateById(petCard) > 0) {
            return new Result(ResultEnum.SUCCESS);
        } else {
            return new Result(ResultEnum.ERROR);
        }
    }
}