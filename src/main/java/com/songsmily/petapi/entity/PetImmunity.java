package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (PetImmunity)表实体类
 *
 * @author SongSmily
 * @since 2020-03-20 11:18:14
 */
@SuppressWarnings("serial")
public class PetImmunity extends Model<PetImmunity> {
    //主键 免疫ID
    @TableId(value = "pet_immunity_id", type = IdType.AUTO)
    private Integer petImmunityId;
    //宠物ID
    private Integer petId;
    //免疫证书ID
    private Integer petCardId;
    //免疫时间
    private Long immunityTime;
    //免疫类型
    private String immunityType;
    //免疫图片URL
    private String immunityImageUrl;
    //免疫信息状态 0、待审核 1、审核通过 2、审核失败
    private Integer immunityStatus;
    //创建时间
    private Long gmtCreate;
    //修改时间
    private Long gmtModified;
    //失败原因
    private String  falseRes;
    //宠物是否注销 0 未注销 1 已注销
    private Integer isCancel;

    public Integer getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Integer isCancel) {
        this.isCancel = isCancel;
    }

    @Override
    public String toString() {
        return "PetImmunity{" +
                "petImmunityId=" + petImmunityId +
                ", petId=" + petId +
                ", petCardId=" + petCardId +
                ", immunityTime=" + immunityTime +
                ", immunityType='" + immunityType + '\'' +
                ", immunityImageUrl='" + immunityImageUrl + '\'' +
                ", immunityStatus=" + immunityStatus +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", falseRes='" + falseRes + '\'' +
                '}';
    }

    public String getFalseRes() {
        return falseRes;
    }

    public void setFalseRes(String falseRes) {
        this.falseRes = falseRes;
    }

    public String getImmunityImageUrl() {
        return immunityImageUrl;
    }

    public void setImmunityImageUrl(String immunityImageUrl) {
        this.immunityImageUrl = immunityImageUrl;
    }

    public Integer getPetImmunityId() {
        return petImmunityId;
    }

    public void setPetImmunityId(Integer petImmunityId) {
        this.petImmunityId = petImmunityId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getPetCardId() {
        return petCardId;
    }

    public void setPetCardId(Integer petCardId) {
        this.petCardId = petCardId;
    }

    public Long getImmunityTime() {
        return immunityTime;
    }

    public void setImmunityTime(Long immunityTime) {
        this.immunityTime = immunityTime;
    }

    public String getImmunityType() {
        return immunityType;
    }

    public void setImmunityType(String immunityType) {
        this.immunityType = immunityType;
    }

    public Integer getImmunityStatus() {
        return immunityStatus;
    }

    public void setImmunityStatus(Integer immunityStatus) {
        this.immunityStatus = immunityStatus;
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

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.petImmunityId;
    }
    }