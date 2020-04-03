package com.songsmily.petapi.entity;

public class PetCardImmunityInfo {
    //宠物ID
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
    //主键 证书ID
    private Integer petCardId;


    //免疫证书编号
    private String cardNumber;

    private String cardImageUrl;
    //证书状态：0、待审核 1、审核通过  2、审核失败  -1、证书失效
    private Integer cardStatus;
    //审核失败原因
    private String falseRes;
    private Integer petImmunityId;
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

    @Override
    public String toString() {
        return "PetCardImmunityInfo{" +
                "petId=" + petId +
                ", petNo='" + petNo + '\'' +
                ", petTypeId=" + petTypeId +
                ", hosteId=" + hosteId +
                ", petName='" + petName + '\'' +
                ", petType='" + petType + '\'' +
                ", petSex='" + petSex + '\'' +
                ", petHeight=" + petHeight +
                ", petBirthday='" + petBirthday + '\'' +
                ", petHairColor='" + petHairColor + '\'' +
                ", petRaiseAddr='" + petRaiseAddr + '\'' +
                ", petWeight=" + petWeight +
                ", petImageUrl='" + petImageUrl + '\'' +
                ", petDesc='" + petDesc + '\'' +
                ", petStatus=" + petStatus +
                ", petCardId=" + petCardId +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardImageUrl='" + cardImageUrl + '\'' +
                ", cardStatus=" + cardStatus +
                ", falseRes='" + falseRes + '\'' +
                ", petImmunityId=" + petImmunityId +
                ", immunityTime=" + immunityTime +
                ", immunityType='" + immunityType + '\'' +
                ", immunityImageUrl='" + immunityImageUrl + '\'' +
                ", immunityStatus=" + immunityStatus +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
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

    public Integer getPetCardId() {
        return petCardId;
    }

    public void setPetCardId(Integer petCardId) {
        this.petCardId = petCardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardImageUrl() {
        return cardImageUrl;
    }

    public void setCardImageUrl(String cardImageUrl) {
        this.cardImageUrl = cardImageUrl;
    }

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getFalseRes() {
        return falseRes;
    }

    public void setFalseRes(String falseRes) {
        this.falseRes = falseRes;
    }

    public Integer getPetImmunityId() {
        return petImmunityId;
    }

    public void setPetImmunityId(Integer petImmunityId) {
        this.petImmunityId = petImmunityId;
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

    public String getImmunityImageUrl() {
        return immunityImageUrl;
    }

    public void setImmunityImageUrl(String immunityImageUrl) {
        this.immunityImageUrl = immunityImageUrl;
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
}
