package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.SelectPageParams;
import com.songsmily.petapi.entity.Log;

import java.util.List;
import java.util.Map;

/**
 * 接口访问日志表(Log)表服务接口
 *
 * @author SongSmily
 * @since 2020-03-16 12:03:45
 */
public interface LogService extends IService<Log> {

    IPage getLogList(SelectPageParams params);

    List<Map> getLogUserNameList();
}