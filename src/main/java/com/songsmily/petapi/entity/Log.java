package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * 接口访问日志表(Log)表实体类
 *
 * @author SongSmily
 * @since 2020-03-16 12:03:45
 */
@SuppressWarnings("serial")
public class Log extends Model<Log> {
    //日志id
    @TableId(value = "log_id", type = IdType.AUTO)

    private Integer logId;
    //请求路径
    private String logUrl;
    //参数
    private String logParams;
    //访问状态，1正常0异常
    private Integer logStatus;
    //异常信息
    private String logMessage;
    //请求方式，get、post等
    private String logMethod;
    //响应时间
    private Long logTime;
    //返回值
    private String logResult;
    //请求ip
    private String logIp;
    //创建时间
    private Long logGmtCreate;
    //操作用户Id
    private Integer logAdminId;
    //操作用户名
    private String logAdminName;
    //操作内容
    private String logDesc;


    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public String getLogParams() {
        return logParams;
    }

    public void setLogParams(String logParams) {
        this.logParams = logParams;
    }

    public Integer getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Integer logStatus) {
        this.logStatus = logStatus;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getLogMethod() {
        return logMethod;
    }

    public void setLogMethod(String logMethod) {
        this.logMethod = logMethod;
    }

    public Long getLogTime() {
        return logTime;
    }

    public void setLogTime(Long logTime) {
        this.logTime = logTime;
    }

    public String getLogResult() {
        return logResult;
    }

    public void setLogResult(String logResult) {
        this.logResult = logResult;
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

    public Integer getLogAdminId() {
        return logAdminId;
    }

    public void setLogAdminId(Integer logAdminId) {
        this.logAdminId = logAdminId;
    }

    public String getLogAdminName() {
        return logAdminName;
    }

    public void setLogAdminName(String logAdminName) {
        this.logAdminName = logAdminName;
    }

    public String getLogDesc() {
        return logDesc;
    }

    public void setLogDesc(String logDesc) {
        this.logDesc = logDesc;
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