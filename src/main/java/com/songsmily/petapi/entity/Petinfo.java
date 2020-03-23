package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.List;

/**
 * 宠物信息表(Petinfo)表实体类
 *
 * @author SongSmily
 * @since 2019-12-20 17:39:00
 */
@SuppressWarnings("serial")
public class Petinfo extends Model<Petinfo> {
    //宠物ID
    @TableId(value = "pet_id", type = IdType.AUTO)
    private Integer petId;
    //宠物编号
    private String petNo;
    //宠物种类编号
    private Integer petTypeId;
    //属主ID
    private Integer hosteId;
    //宠物名称
    private String petName;
    //宠物类型
    private String petType;
    //注册日期
    private Long gmtCreate;
    //修改日期
    private Long gmtModified;
    //宠物性别
    private String petSex;
    //宠物身高
    private Integer petHeight;
    //宠物出生日期
    private String petBirthday;
    //宠物毛色
    private String petHairColor;
    //宠物饲养地址
    private String petRaiseAddr;
    //宠物体重
    private Integer petWeight;
    //宠物图片地址
    private String petImageUrl;
    //宠物描述
    private String petDesc;
    //宠物状态
    private Integer petStatus;

    //宠物免疫证书 以及免疫信息
    @TableField(exist = false)
    private PetCard petCard;

    public PetCard getPetCards() {
        return petCard;
    }

    public void setPetCard(PetCard petCard) {
        this.petCard = petCard;
    }

    @Override
    public String toString() {
        return "Petinfo{" +
                "petId=" + petId +
                ", petNo='" + petNo + '\'' +
                ", petTypeId=" + petTypeId +
                ", hosteId=" + hosteId +
                ", petName='" + petName + '\'' +
                ", petType='" + petType + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", petSex='" + petSex + '\'' +
                ", petHeight=" + petHeight +
                ", petBirthday='" + petBirthday + '\'' +
                ", petHairColor='" + petHairColor + '\'' +
                ", petRaiseAddr='" + petRaiseAddr + '\'' +
                ", petWeight=" + petWeight +
                ", petImageUrl='" + petImageUrl + '\'' +
                ", petDesc='" + petDesc + '\'' +
                ", petStatus=" + petStatus +
                ", petCards=" + petCard +
                '}';
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getPetNo() {
        return petNo;
    }

    public void setPetNo(String petNo) {
        this.petNo = petNo;
    }

    public Integer getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(Integer petTypeId) {
        this.petTypeId = petTypeId;
    }

    public Integer getHosteId() {
        return hosteId;
    }

    public void setHosteId(Integer hosteId) {
        this.hosteId = hosteId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
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

    public String getPetSex() {
        return petSex;
    }

    public void setPetSex(String petSex) {
        this.petSex = petSex;
    }

    public Integer getPetHeight() {
        return petHeight;
    }

    public void setPetHeight(Integer petHeight) {
        this.petHeight = petHeight;
    }

    public String getPetBirthday() {
        return petBirthday;
    }

    public void setPetBirthday(String petBirthday) {
        this.petBirthday = petBirthday;
    }

    public String getPetHairColor() {
        return petHairColor;
    }

    public void setPetHairColor(String petHairColor) {
        this.petHairColor = petHairColor;
    }

    public String getPetRaiseAddr() {
        return petRaiseAddr;
    }

    public void setPetRaiseAddr(String petRaiseAddr) {
        this.petRaiseAddr = petRaiseAddr;
    }

    public Integer getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(Integer petWeight) {
        this.petWeight = petWeight;
    }

    public String getPetImageUrl() {
        return petImageUrl;
    }

    public void setPetImageUrl(String petImageUrl) {
        this.petImageUrl = petImageUrl;
    }

    public String getPetDesc() {
        return petDesc;
    }

    public void setPetDesc(String petDesc) {
        this.petDesc = petDesc;
    }

    public Integer getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(Integer petStatus) {
        this.petStatus = petStatus;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.petId;
    }
    }