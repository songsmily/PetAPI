package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.BlogSelectParams;
import com.songsmily.petapi.entity.Information;

/**
 * (Information)表服务接口
 *
 * @author SongSmily
 * @since 2020-04-14 08:09:45
 */
public interface InformationService extends IService<Information> {

    boolean addInformation(Information information);

    IPage getInfos(BlogSelectParams params);

    boolean deleteInfoById(String infoId);
}