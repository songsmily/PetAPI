package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (Log)表实体类
 *
 * @author SongSmily
 * @since 2020-04-16 22:41:24
 */
@SuppressWarnings("serial")
public class Log extends Model<Log> {
    //日志id
    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;
    //操作用户Id
    private Integer logAdminId;
    //操作用户名
    private String logUsername;
    //操作名称
    private String logOperate;
    //方法名
    private String logMethod;
    //请求参数
    private String logParams;
    //请求ip
    private String logIp;
    //创建时间
    private Long logGmtCreate;


    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getLogAdminId() {
        return logAdminId;
    }

    public void setLogAdminId(Integer logAdminId) {
        this.logAdminId = logAdminId;
    }

    public String getLogUsername() {
        return logUsername;
    }

    public void setLogUsername(String logUsername) {
        this.logUsername = logUsername;
    }

    public String getLogOperate() {
        return logOperate;
    }

    public void setLogOperate(String logOperate) {
        this.logOperate = logOperate;
    }

    public String getLogMethod() {
        return logMethod;
    }

    public void setLogMethod(String logMethod) {
        this.logMethod = logMethod;
    }

    public String getLogParams() {
        return logParams;
    }

    public void setLogParams(String logParams) {
        this.logParams = logParams;
    }

    public String getLogIp() {
        return logIp;
    }

    public void setLogIp(String logIp) {
        this.logIp = logIp;
    }

    public Long getLogGmtCreate() {
        return logGmtCreate;
    }

    public void setLogGmtCreate(Long logGmtCreate) {
        this.logGmtCreate = logGmtCreate;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.logId;
    }
    }