package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.InformationDao;
import com.songsmily.petapi.dto.BlogSelectParams;
import com.songsmily.petapi.entity.Information;
import com.songsmily.petapi.service.InformationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * (Information)表服务实现类
 *
 * @author SongSmily
 * @since 2020-04-14 08:09:45
 */
@Service("informationService")
public class InformationServiceImpl extends ServiceImpl<InformationDao, Information> implements InformationService {
    @Resource
    InformationDao informationDao;

    /**
     * 上传资讯
     * @param information
     * @return
     */
    @Override
    public boolean addInformation(Information information) {
        information.setCreatedTime(System.currentTimeMillis());
        information.setInfoId(UUID.randomUUID().toString());
        int insert = informationDao.insert(information);

        return insert > 0;
    }

    /**
     * 返回资讯列表
     * @param params
     * @return
     */
    @Override
    public IPage getInfos(BlogSelectParams params) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("deleted",0);
        Page page = new Page(params.getCurrentPage(), params.getPageSize());
        IPage iPage = informationDao.selectPage(page, wrapper);

        return iPage;
    }

    /**
     * 根据 ID删除资讯 逻辑删除
     * @param infoId
     * @return
     */
    @Override
    public boolean deleteInfoById(String infoId) {
        Information information = informationDao.selectById(infoId);
        information.setDeleted(1);
        int i = informationDao.updateById(information);
        return i > 0;
    }
}