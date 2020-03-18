package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.LogDao;
import com.songsmily.petapi.entity.Log;
import com.songsmily.petapi.service.LogService;
import org.springframework.stereotype.Service;

/**
 * 接口访问日志表(Log)表服务实现类
 *
 * @author SongSmily
 * @since 2020-03-16 12:03:45
 */
@Service("logService")
public class LogServiceImpl extends ServiceImpl<LogDao, Log> implements LogService {

}