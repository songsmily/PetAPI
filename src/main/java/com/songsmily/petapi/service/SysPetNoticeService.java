package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.SysPetNotice;

/**
 * (SysPetNotice)表服务接口
 *
 * @author SongSmily
 * @since 2020-03-18 08:25:49
 */
public interface SysPetNoticeService extends IService<SysPetNotice> {

    Result getMessage(Integer topNumber);

    Result haveRead(Integer id);

    Result deleteNotice(Integer id);


    Result allHaveRead();

    Result deleteAllNotice();
}