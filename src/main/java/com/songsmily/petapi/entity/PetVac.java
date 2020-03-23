package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (PetVac)表实体类
 *
 * @author SongSmily
 * @since 2020-03-20 16:13:22
 */
@SuppressWarnings("serial")
public class PetVac extends Model<PetVac> {
    //疫苗ID
    private Integer vacId;
    //疫苗名称
    private String vacName;
    //疫苗描述
    private String vacDesc;
    //是否核心疫苗
    private Integer isMust;
    //疫苗适用宠物类型 宠物猫 宠物狗
    private String petClassifyOne;
    //疫苗过期时间
    private Long gmtPass;


    public Integer getVacId() {
        return vacId;
    }

    public void setVacId(Integer vacId) {
        this.vacId = vacId;
    }

    public String getVacName() {
        return vacName;
    }

    public void setVacName(String vacName) {
        this.vacName = vacName;
    }

    public String getVacDesc() {
        return vacDesc;
    }

    public void setVacDesc(String vacDesc) {
        this.vacDesc = vacDesc;
    }

    public Integer getIsMust() {
        return isMust;
    }

    public void setIsMust(Integer isMust) {
        this.isMust = isMust;
    }

    public String getPetClassifyOne() {
        return petClassifyOne;
    }

    public void setPetClassifyOne(String petClassifyOne) {
        this.petClassifyOne = petClassifyOne;
    }

    public Long getGmtPass() {
        return gmtPass;
    }

    public void setGmtPass(Long gmtPass) {
        this.gmtPass = gmtPass;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.vacId;
    }
    }