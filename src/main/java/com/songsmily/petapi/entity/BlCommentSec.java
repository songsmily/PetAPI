package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * 评论表(BlCommentSec)表实体类
 *
 * @author SongSmily
 * @since 2020-04-08 10:36:33
 */
@SuppressWarnings("serial")
public class BlCommentSec extends Model<BlCommentSec> {
    //二级评论id
    @TableId(value = "sec_id")
    private String secId;
    //二级评论内容
    private String secContent;
    //二级评价人
    private Integer secUser;
    //父评论id
    private String secParent;
    //点赞数
    private Integer secGood;
    //评论时间
    private Long createdTime;
    //是否删除，0否1是
    private Integer deleted;
    //审核状态 0 未审核  1 已审核
    private Integer secStatus;
    //评论人昵称
    @TableField(exist = false)
    private String secCommentUserName;
    //评论人头像
    @TableField(exist = false)
    private String secCommentUserAvatarUrl;

    public String getSecCommentUserName() {
        return secCommentUserName;
    }

    public void setSecCommentUserName(String secCommentUserName) {
        this.secCommentUserName = secCommentUserName;
    }

    public String getSecCommentUserAvatarUrl() {
        return secCommentUserAvatarUrl;
    }

    public void setSecCommentUserAvatarUrl(String secCommentUserAvatarUrl) {
        this.secCommentUserAvatarUrl = secCommentUserAvatarUrl;
    }

    public Integer getSecStatus() {
        return secStatus;
    }

    public void setSecStatus(Integer secStatus) {
        this.secStatus = secStatus;
    }

    @Override
    public String toString() {
        return "BlCommentSec{" +
                "secId='" + secId + '\'' +
                ", secContent='" + secContent + '\'' +
                ", secUser=" + secUser +
                ", secParent='" + secParent + '\'' +
                ", secGood=" + secGood +
                ", createdTime=" + createdTime +
                ", deleted=" + deleted +
                '}';
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSecContent() {
        return secContent;
    }

    public void setSecContent(String secContent) {
        this.secContent = secContent;
    }

    public Integer getSecUser() {
        return secUser;
    }

    public void setSecUser(Integer secUser) {
        this.secUser = secUser;
    }

    public String getSecParent() {
        return secParent;
    }

    public void setSecParent(String secParent) {
        this.secParent = secParent;
    }

    public Integer getSecGood() {
        return secGood;
    }

    public void setSecGood(Integer secGood) {
        this.secGood = secGood;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.secId;
    }
    }