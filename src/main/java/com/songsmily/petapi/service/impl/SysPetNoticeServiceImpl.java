package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.SysPetNoticeDao;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.SysPetNotice;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.enums.ResultEnum;
import com.songsmily.petapi.service.SysPetNoticeService;
import com.songsmily.petapi.utils.ShiroUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (SysPetNotice)表服务实现类
 *
 * @author SongSmily
 * @since 2020-03-18 08:25:49
 */
@Service("sysPetNoticeService")
public class SysPetNoticeServiceImpl extends ServiceImpl<SysPetNoticeDao, SysPetNotice> implements SysPetNoticeService {
    @Resource
    SysPetNoticeDao sysPetNoticeDao;

    @Override
    public Result getMessage(Integer topNumber) {
        User user = ShiroUtil.getUser(new User());
        List<SysPetNotice> list = sysPetNoticeDao.getMessage(topNumber, user.getId());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getId());
        wrapper.eq("notice_status", 0);

        Integer count = sysPetNoticeDao.selectCount(wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("unReadCount", count);
        return new Result(map);
    }

    @Override
    public Result haveRead(Integer id) {
        SysPetNotice sysPetNotice = new SysPetNotice();
        sysPetNotice.setId(id);
        sysPetNotice.setNoticeStatus(1);
        if (sysPetNoticeDao.updateById(sysPetNotice) == 1) {
            return new Result(ResultEnum.SUCCESS);
        } else {
            return new Result(ResultEnum.ERROR);
        }
    }

    @Override
    public Result deleteNotice(Integer id) {
        if (sysPetNoticeDao.deleteById(id) == 1) {
            return new Result(ResultEnum.SUCCESS);
        } else {
            return new Result(ResultEnum.ERROR);
        }
    }

    @Override
    public Result allHaveRead() {
        User user = ShiroUtil.getUser(new User());
        if (sysPetNoticeDao.allHaveRead(user.getId()) > 0) {
            return new Result(ResultEnum.SUCCESS);
        } else {
            return new Result(ResultEnum.ERROR);

        }
    }

    @Override
    public Result deleteAllNotice() {
        User user = ShiroUtil.getUser(new User());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getId());
        if (sysPetNoticeDao.delete(wrapper) > 0) {
            return new Result(ResultEnum.SUCCESS);
        } else {
            return new Result(ResultEnum.ERROR);
        }
    }
}