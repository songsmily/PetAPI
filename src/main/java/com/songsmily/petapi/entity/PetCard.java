package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.List;

/**
 * (PetCard)表实体类
 *
 * @author SongSmily
 * @since 2020-03-20 11:17:52
 */
@SuppressWarnings("serial")
public class PetCard extends Model<PetCard> {
    //主键 证书ID
    @TableId(value = "pet_card_id", type = IdType.AUTO)
    private Integer petCardId;
    //宠物ID
    private Integer petId;

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    //免疫证书编号
    private String cardNumber;
    private String cardImageUrl;
    //证书状态：0、待审核 1、审核通过  2、审核失败  -1、证书失效
    private Integer cardStatus;
    //创建时间
    private Long gmtCreated;
    //修改时间
    private Long gmtModified;
    //审核失败原因
    private String falseRes;

    public String getFalseRes() {
        return falseRes;
    }

    public void setFalseRes(String falseRes) {
        this.falseRes = falseRes;
    }

    @TableField(exist = false)
    private List<PetImmunity> petImmunities;

    public String getCardImageUrl() {
        return cardImageUrl;
    }

    @Override
    public String toString() {
        return "PetCard{" +
                "petCardId=" + petCardId +
                ", petId=" + petId +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardImageUrl='" + cardImageUrl + '\'' +
                ", cardStatus=" + cardStatus +
                ", gmtCreated=" + gmtCreated +
                ", gmtModified=" + gmtModified +
                ", falseRes='" + falseRes + '\'' +
                ", petImmunities=" + petImmunities +
                '}';
    }

    public void setCardImageUrl(String cardImageUrl) {
        this.cardImageUrl = cardImageUrl;
    }

    public List<PetImmunity> getPetImmunities() {
        return petImmunities;
    }

    public void setPetImmunities(List<PetImmunity> petImmunities) {
        this.petImmunities = petImmunities;
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

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

    public Long getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Long gmtCreated) {
        this.gmtCreated = gmtCreated;
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
        return this.petCardId;
    }
    }