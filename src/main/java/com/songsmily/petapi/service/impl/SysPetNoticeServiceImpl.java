package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.SysPetNoticeDao;
import com.songsmily.petapi.entity.SysPetNotice;
import com.songsmily.petapi.service.SysPetNoticeService;
import org.springframework.stereotype.Service;

/**
 * (SysPetNotice)表服务实现类
 *
 * @author SongSmily
 * @since 2020-03-18 08:25:49
 */
@Service("sysPetNoticeService")
public class SysPetNoticeServiceImpl extends ServiceImpl<SysPetNoticeDao, SysPetNotice> implements SysPetNoticeService {

}