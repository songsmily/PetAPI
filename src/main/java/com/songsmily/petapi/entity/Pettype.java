package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (Pettype)表实体类
 *
 * @author SongSmily
 * @since 2019-12-20 19:30:28
 */
@SuppressWarnings("serial")
public class Pettype extends Model<Pettype> {
    //宠物类型ID
    private Integer petTypeId;
    //宠物一级分类
    private String petClassifyOne;
    //宠物二级分类
    private String petClassifyTwo;
    //宠物三级分类
    private String petClassifyThree;


    public Integer getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(Integer petTypeId) {
        this.petTypeId = petTypeId;
    }

    public String getPetClassifyOne() {
        return petClassifyOne;
    }

    public void setPetClassifyOne(String petClassifyOne) {
        this.petClassifyOne = petClassifyOne;
    }

    public String getPetClassifyTwo() {
        return petClassifyTwo;
    }

    public void setPetClassifyTwo(String petClassifyTwo) {
        this.petClassifyTwo = petClassifyTwo;
    }

    public String getPetClassifyThree() {
        return petClassifyThree;
    }

    public void setPetClassifyThree(String petClassifyThree) {
        this.petClassifyThree = petClassifyThree;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.petTypeId;
    }

    @Override
    public String toString() {
        return "Pettype{" +
                "petTypeId=" + petTypeId +
                ", petClassifyOne='" + petClassifyOne + '\'' +
                ", petClassifyTwo='" + petClassifyTwo + '\'' +
                ", petClassifyThree='" + petClassifyThree + '\'' +
                '}';
    }
}