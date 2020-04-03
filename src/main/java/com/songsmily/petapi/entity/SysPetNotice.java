package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (SysPetNotice)表实体类
 *
 * @author SongSmily
 * @since 2020-03-18 09:03:17
 */
@SuppressWarnings("serial")
public class SysPetNotice extends Model<SysPetNotice> {
    //唯一标识
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //用户ID
    private Integer userId;
    //宠物ID
    private Integer petId;
    //宠物免疫证书ID
    private Integer petCardId;
    //宠物免疫信息ID
    private Integer petImmunityId;
    //通知类型：1、审核通过通知 2、审核失败通知 3、免疫证书审核通过通知 4、免疫证书审核失败通知 5、免疫信息审核通过通知 6、免疫信息审核失败通知
    private Integer type;
    
    private Long gmtCreate;

    private  String noticeDesc;
    //通知状态 0、未读状态 1、已读状态 -1、删除状态
    private Integer noticeStatus;

    @Override
    public String toString() {
        return "SysPetNotice{" +
                "id=" + id +
                ", userId=" + userId +
                ", petId=" + petId +
                ", petCardId=" + petCardId +
                ", petImmunityId=" + petImmunityId +
                ", type=" + type +
                ", gmtCreate=" + gmtCreate +
                ", noticeDesc='" + noticeDesc + '\'' +
                ", noticeStatus=" + noticeStatus +
                '}';
    }

    public String getNoticeDesc() {
        return noticeDesc;
    }

    public void setNoticeDesc(String noticeDesc) {
        this.noticeDesc = noticeDesc;
    }

    public Integer getPetCardId() {
        return petCardId;
    }

    public void setPetCardId(Integer petCardId) {
        this.petCardId = petCardId;
    }

    public Integer getPetImmunityId() {
        return petImmunityId;
    }

    public void setPetImmunityId(Integer petImmunityId) {
        this.petImmunityId = petImmunityId;
    }

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

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
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