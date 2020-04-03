package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.songsmily.petapi.dao.*;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.*;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.PetCheckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PetCheckServiceImpl implements PetCheckService {
    @Resource
    PetinfoDao petinfoDao;
    @Resource
    UserDao userDao;
    @Resource
    PetCardDao petCardDao;
    @Resource
    PetCheckfalseDao petCheckfalseDao;
    @Resource
    PetImmunityDao petImmunityDao;
    @Resource
    SysPetNoticeDao sysPetNoticeDao;

    @Override
    public Result selectUnCheckList(Integer currentPage, Integer pageSize, String areaFilter) {
        if (areaFilter.equals("-1")) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("pet_status", 0);
            wrapper.eq("is_cancel", 0);
            Page page = new Page(currentPage, pageSize);

            IPage iPage = petinfoDao.selectPage(page, wrapper);

            return new Result(iPage);
        } else {
            Integer begin = (currentPage - 1) * pageSize;
            Integer end = pageSize;

            Map<String, Object> selectMap = new HashMap<>();
            Map<String, Object> map = new HashMap<>();
            List<String> userIDs = userDao.selectUserIdByArea(areaFilter);
            if (userIDs.size() > 0) {
                selectMap.put("begin", begin);
                selectMap.put("end", end);
                selectMap.put("userIDs", userIDs);
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.in("hoste_id", userIDs);
                wrapper.eq("pet_status", 0);
                wrapper.eq("is_cancel", 0);
                Integer count = petinfoDao.selectCount(wrapper);
                map.put("total", count);
                map.put("pages", Math.ceil(count / pageSize));
                List<Petinfo> petinfos = petinfoDao.returnUnCheckPetInfo(selectMap);
                map.put("records", petinfos);
                return new Result(map);
            }
            map.put("total", 0);
            map.put("pages", 1);
            map.put("records", null);
            return new Result(map);

        }


    }

    @Override
    public Result getPetAndHostInfoById(Integer petId) {

        Petinfo petinfo = petinfoDao.selectById(petId);
        User user = userDao.selectById(petinfo.getHosteId());
        Map<String, Object> map = new HashMap<>();
        map.put("petInfo", petinfo);
        map.put("userInfo", user);

        return new Result(map);
    }

    /**
     * @param petId    宠物ID
     * @param falseRes 审核失败原因
     * @return
     */
    @Override
    @Transactional
    public Result doCheckFalse(Integer petId, String falseRes) {
        PetCheckfalse petCheckfalse = new PetCheckfalse();
        petCheckfalse.setPetId(petId);
        petCheckfalse.setFalseRes(falseRes);
        petCheckfalse.setGmtCreate(System.currentTimeMillis());
        Petinfo petinfo = petinfoDao.selectById(petId);
        petinfo.setPetStatus(-1);//设置属性为-1，审核失败
        SysPetNotice sysPetNotice = new SysPetNotice();//系统通知对象
        sysPetNotice.setGmtCreate(System.currentTimeMillis());
        sysPetNotice.setPetId(petinfo.getPetId());
        sysPetNotice.setUserId(petinfo.getHosteId());
        sysPetNotice.setType(2);//审核失败通知
        sysPetNotice.setNoticeStatus(0);//通知未读状态
        if (petCheckfalseDao.insert(petCheckfalse) != 0 && petinfoDao.updateById(petinfo) != 0 && sysPetNoticeDao.insert(sysPetNotice) != 0) {
            return new Result(ResultEnum.SUCCESS);
        } else {
            return new Result(ResultEnum.ERROR);
        }
    }

    @Override
    @Transactional
    public Result doCheckPass(Integer petId) {
        Petinfo petinfo = petinfoDao.selectById(petId);
        petinfo.setPetStatus(1);
        SysPetNotice sysPetNotice = new SysPetNotice();//系统通知对象
        sysPetNotice.setGmtCreate(System.currentTimeMillis());
        sysPetNotice.setPetId(petinfo.getPetId());
        sysPetNotice.setUserId(petinfo.getHosteId());
        sysPetNotice.setType(1);//审核通过通知
        sysPetNotice.setNoticeStatus(0);//通知未读状态
        if (petinfoDao.updateById(petinfo) != 0 && sysPetNoticeDao.insert(sysPetNotice) != 0) {
            return new Result(ResultEnum.SUCCESS);

        } else {
            return new Result(ResultEnum.ERROR);
        }
    }

    @Override
    public Result returnUnCheckCardPetInfo(Integer currentPage, Integer pageSize, String areaFilter) {

        Integer begin = (currentPage - 1) * pageSize;
        Integer end = pageSize;
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("begin", begin);
        selectMap.put("end", end);
        Integer total;
        List<Petinfo> petinfos;
        if (areaFilter.equals("-1")) {
            selectMap.put("userIDs", null);
            petinfos = petinfoDao.returnUnCheckCardPetInfo(selectMap);
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("card_status", 0);
            wrapper.eq("is_cancel", 0);
            total = petCardDao.selectCount(wrapper);
        } else {
            List<String> userIDs = userDao.selectUserIdByArea(areaFilter);
            if (userIDs.size() > 0) {
                selectMap.put("userIDs", userIDs);
                petinfos = petinfoDao.returnUnCheckCardPetInfo(selectMap);
                total = petinfoDao.returnUnCheckCardPetInfoCountByUserIDs(userIDs);
            } else {
                total = 0;
                petinfos = null;
            }

        }

        map.put("total", total);
        map.put("records", petinfos);
        map.put("pages", Math.ceil(total / pageSize));
        return new Result(map);
    }

    @Transactional
    @Override
    public Result doCheckCard(PetCard petCard, boolean b) {
        SysPetNotice sysPetNotice = new SysPetNotice();
        sysPetNotice.setPetCardId(petCard.getPetCardId());
        sysPetNotice.setPetId(petCard.getPetId());
        sysPetNotice.setUserId(petinfoDao.selectById(petCard.getPetId()).getHosteId());
        sysPetNotice.setGmtCreate(System.currentTimeMillis());
        sysPetNotice.setNoticeStatus(0);
        Petinfo petinfo = petinfoDao.selectById(petCard.getPetId());
        if (b) {
            petinfo.setPetCardCount(null == petinfo.getPetCardCount() ? 1 : petinfo.getPetCardCount() + 1);
            petCard.setCardStatus(1);
            sysPetNotice.setType(3);//免疫证书审核通过类型
        } else {
            petCard.setCardStatus(2);
            sysPetNotice.setType(4);//免疫证书审核失败类型
        }
        if (b){
            if (petCardDao.updateById(petCard) > 0 && sysPetNoticeDao.insert(sysPetNotice) > 0 && petinfoDao.updateById(petinfo) > 0) {
                return new Result(ResultEnum.SUCCESS);
            } else {
                return new Result(ResultEnum.ERROR);
            }
        }else {
            if (petCardDao.updateById(petCard) > 0 && sysPetNoticeDao.insert(sysPetNotice) > 0 ) {
                return new Result(ResultEnum.SUCCESS);
            } else {
                return new Result(ResultEnum.ERROR);
            }
        }

    }

    @Override
    public Result returnUnCheckCardImmunityPetInfo(Integer currentPage, Integer pageSize, String areaFilter) {
        Integer begin = (currentPage - 1) * pageSize;
        Integer end = pageSize;
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("begin", begin);
        selectMap.put("end", end);
        selectMap.put("unCheck", 1);
        Integer total;
        List<PetCardImmunityInfo> petinfos;
        if (areaFilter.equals("-1")) {
            selectMap.put("userIDs", null);
            petinfos = petinfoDao.returnPetCardImmunityInfosByUserIDs(selectMap);
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("immunity_status", 0);
            wrapper.eq("is_cancel", 0);
            total = petImmunityDao.selectCount(wrapper);
        } else {
            List<String> userIDs = userDao.selectUserIdByArea(areaFilter);
            if (userIDs.size() > 0) {
                selectMap.put("userIDs", userIDs);
                petinfos = petinfoDao.returnPetCardImmunityInfosByUserIDs(selectMap);
                total = petinfoDao.returnPetCardImmunityInfosCountByUserIDs(selectMap);
            } else {
                total = 0;
                petinfos = null;
            }
        }
        map.put("total", total);
        map.put("records", petinfos);
        map.put("pages", Math.ceil(total / pageSize));
        return new Result(map);
    }

    @Transactional
    @Override
    public Result doCheckImmunity(PetImmunity petImmunity, boolean b) {
        SysPetNotice sysPetNotice = new SysPetNotice();
        sysPetNotice.setUserId(petinfoDao.selectById(petImmunity.getPetId()).getHosteId());
        sysPetNotice.setPetId(petImmunity.getPetId());
        sysPetNotice.setPetCardId(petImmunity.getPetCardId());
        sysPetNotice.setPetImmunityId(petImmunity.getPetImmunityId());
        PetCard petCard = petCardDao.selectById(petImmunity.getPetCardId());

        if (b){
            if (null == petCard.getImmunityCount()){
                petCard.setImmunityCount(1);
            }else{
                petCard.setImmunityCount(petCard.getImmunityCount() + 1);
            }
            petImmunity.setImmunityStatus(1);
            sysPetNotice.setType(5);//免疫信息审核通过
        }else {
            petImmunity.setImmunityStatus(2);
            sysPetNotice.setType(6);//免疫信息审核失败
        }
        sysPetNotice.setGmtCreate(System.currentTimeMillis());
        sysPetNotice.setNoticeStatus(0);
        if (b){
            if (sysPetNoticeDao.insert(sysPetNotice) > 0 && petImmunityDao.updateById(petImmunity) > 0 && petCardDao.updateById(petCard) > 0){
                return new Result(ResultEnum.SUCCESS);
            }
        }else{
            if (sysPetNoticeDao.insert(sysPetNotice) > 0 && petImmunityDao.updateById(petImmunity) > 0){
                return new Result(ResultEnum.SUCCESS);
            }
        }

        return new Result(ResultEnum.ERROR);
    }
}
