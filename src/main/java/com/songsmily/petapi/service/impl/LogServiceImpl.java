package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.LogDao;
import com.songsmily.petapi.dto.SelectPageParams;
import com.songsmily.petapi.entity.Log;
import com.songsmily.petapi.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口访问日志表(Log)表服务实现类
 *
 * @author SongSmily
 * @since 2020-03-16 12:03:45
 */
@Service("logService")
public class LogServiceImpl extends ServiceImpl<LogDao, Log> implements LogService {

    @Resource
    LogDao logDao;

    /**
     * 查询日志列表
     * @param params
     * @return
     */
    @Override
    public IPage getLogList(SelectPageParams params) {
        Page page = new Page(params.getCurrentPage(), params.getPageSize());
        QueryWrapper wrapper = new QueryWrapper();
        if (params.getLogAdminId() != -1){
            wrapper.eq("log_admin_id",params.getLogAdminId());
        }

        wrapper.orderByDesc("log_gmt_create");
        IPage iPage = logDao.selectPage(page, wrapper);
        return iPage;
    }

    @Override
    public List<Map> getLogUserNameList() {
        List<Log> logs = logDao.selectDisUserNameList();
        List<Map> result = new ArrayList<>();
        Map<String, Object> map;
        for (Log log:
             logs) {
            map = new HashMap<>();
            map.put("value",log.getLogUsername());
            map.put("id",log.getLogAdminId());
            result.add(map);
        }
        return result;
    }
}