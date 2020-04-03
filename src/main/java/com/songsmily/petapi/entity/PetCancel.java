package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (PetCancel)表实体类
 *
 * @author SongSmily
 * @since 2020-04-02 04:41:38
 */
@SuppressWarnings("serial")
public class PetCancel extends Model<PetCancel> {
    //主键
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //宠物ID
    private Integer petId;
    //宠物编号
    private Long petNo;
    //宠物属主ID
    private Integer petHosteId;
    //宠物注销类型
    private String cancelType;
    //宠物注销原因
    private String cancelRes;
    //注销失败原因
    private String falseRes;
    //创建时间
    private Long gmtCreate;
    //修改时间
    private Long gmtModified;
    //状态 0:注销中 1：已注销 2：注销失败 -1：已删除
    private Integer cancelStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Long getPetNo() {
        return petNo;
    }

    public void setPetNo(Long petNo) {
        this.petNo = petNo;
    }

    public Integer getPetHosteId() {
        return petHosteId;
    }

    public void setPetHosteId(Integer petHosteId) {
        this.petHosteId = petHosteId;
    }

    public String getCancelType() {
        return cancelType;
    }

    public void setCancelType(String cancelType) {
        this.cancelType = cancelType;
    }

    public String getCancelRes() {
        return cancelRes;
    }

    public void setCancelRes(String cancelRes) {
        this.cancelRes = cancelRes;
    }

    public String getFalseRes() {
        return falseRes;
    }

    public void setFalseRes(String falseRes) {
        this.falseRes = falseRes;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(Integer cancelStatus) {
        this.cancelStatus = cancelStatus;
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