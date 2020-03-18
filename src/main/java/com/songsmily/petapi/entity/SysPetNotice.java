package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (SysPetNotice)表实体类
 *
 * @author SongSmily
 * @since 2020-03-18 08:25:49
 */
@SuppressWarnings("serial")
public class SysPetNotice extends Model<SysPetNotice> {
    //唯一标识
    private Integer id;
    //用户ID
    private Integer userId;
    //宠物ID
    private Integer petId;
    //通知类型：1、审核通过通知 2、审核失败通知 3、防疫信息审核通过通知 4、防疫信息审核失败通知
    private Integer type;
    
    private String gmtCreate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    }