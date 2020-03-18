package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (PetCheckfalse)表实体类
 *
 * @author SongSmily
 * @since 2020-03-15 09:07:40
 */
@SuppressWarnings("serial")
public class PetCheckfalse extends Model<PetCheckfalse> {
    @TableId(value = "pet_id", type = IdType.AUTO)
    private Integer id;
    
    private Integer petId;
    
    private String falseRes;
    
    private Long gmtCreate;


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