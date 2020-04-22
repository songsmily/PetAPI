package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 评论表(BlComment)表实体类
 *
 * @author SongSmily
 * @since 2020-04-07 15:44:18
 */
@SuppressWarnings("serial")
public class BlComment extends Model<BlComment>  implements Serializable{
    //评论id
    @TableId(value = "comment_id")
    private String commentId;
    //评论内容
    private String commentContent;
    //评价人
    private Integer commentUser;
    //评论帖子id
    private String commentBlog;
    //点赞数
    private Integer commentGood;
    //评论数
    private String commentSecond;
    //评论时间
    private Long createdTime;
    //审核状态 0 未审核  1 已审核
    private Integer commentStatus;
    //是否删除，0否1是
    private Integer deleted;

    //当前用户是否点赞
    @TableField(exist = false)
    private Boolean isCommentGood;
    //点赞总数
    @TableField(exist = false)
    private Integer commentGoodCount;
    //评论人昵称
    @TableField(exist = false)
    private String commentUserName;
    //评论人头像
    @TableField(exist = false)
    private String commentUserAvatarUrl;

    //二级评论
    @TableField(exist = false)
    private List<BlCommentSec> commentSecList;

    @Override
    public String toString() {
        return "BlComment{" +
                "commentId='" + commentId + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", commentUser=" + commentUser +
                ", commentBlog='" + commentBlog + '\'' +
                ", commentGood=" + commentGood +
                ", commentSecond='" + commentSecond + '\'' +
                ", createdTime=" + createdTime +
                ", deleted=" + deleted +
                ", isCommentGood=" + isCommentGood +
                ", commentGoodCount=" + commentGoodCount +
                ", commentUserName='" + commentUserName + '\'' +
                ", commentUserAvatarUrl='" + commentUserAvatarUrl + '\'' +
                ", commentSecList=" + commentSecList +
                '}';
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(Integer commentUser) {
        this.commentUser = commentUser;
    }

    public String getCommentBlog() {
        return commentBlog;
    }

    public void setCommentBlog(String commentBlog) {
        this.commentBlog = commentBlog;
    }

    public Integer getCommentGood() {
        return commentGood;
    }

    public void setCommentGood(Boolean commentGood) {
        isCommentGood = commentGood;
    }

    public Integer getCommentGoodCount() {
        return commentGoodCount;
    }

    public void setCommentGoodCount(Integer commentGoodCount) {
        this.commentGoodCount = commentGoodCount;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getCommentUserAvatarUrl() {
        return commentUserAvatarUrl;
    }

    public void setCommentUserAvatarUrl(String commentUserAvatarUrl) {
        this.commentUserAvatarUrl = commentUserAvatarUrl;
    }

    public List<BlCommentSec> getCommentSecList() {
        return commentSecList;
    }

    public void setCommentSecList(List<BlCommentSec> commentSecList) {
        this.commentSecList = commentSecList;
    }

    public void setCommentGood(Integer commentGood) {
        this.commentGood = commentGood;
    }

    public String getCommentSecond() {
        return commentSecond;
    }

    public void setCommentSecond(String commentSecond) {
        this.commentSecond = commentSecond;
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
        return this.commentId;
    }
    }