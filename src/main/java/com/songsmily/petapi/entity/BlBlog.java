package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 博客表(BlBlog)表实体类
 *
 * @author SongSmily
 * @since 2020-04-06 11:01:31
 */
@SuppressWarnings("serial")
public class BlBlog extends Model<BlBlog> implements Serializable {
    //帖子id
    @TableId(value = "blog_id")
    private String blogId;
    //帖子分类 1：分享 2：提问 3：讨论
    private Integer blogType;
    //标题
    private String blogTitle;
    //帖子标签 . 分割
    private String blogTag;
    //用户id
    private Integer userId;
    //封面
    private String blogImage;
    //帖子内容
    private String blogContent;
    //简介
    private String blogRemark;
    //点赞数
    private Integer blogGoods;
    //阅读数
    private Integer blogRead;
    //收藏数
    private Integer blogCollect;
    //评论数
    private Integer blogComment;
    //创建时间
    private Long createdTime;
    //更新时间
    private Long updateTime;
    //乐观锁
    private Integer version;
    //是否删除，0否1是
    private Integer deleted;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String avatarUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public Integer getBlogType() {
        return blogType;
    }

    public void setBlogType(Integer blogType) {
        this.blogType = blogType;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogTag() {
        return blogTag;
    }

    public void setBlogTag(String blogTag) {
        this.blogTag = blogTag;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBlogImage() {
        return blogImage;
    }

    public void setBlogImage(String blogImage) {
        this.blogImage = blogImage;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public String getBlogRemark() {
        return blogRemark;
    }

    public void setBlogRemark(String blogRemark) {
        this.blogRemark = blogRemark;
    }

    public Integer getBlogGoods() {
        return blogGoods;
    }

    public void setBlogGoods(Integer blogGoods) {
        this.blogGoods = blogGoods;
    }

    public Integer getBlogRead() {
        return blogRead;
    }

    public void setBlogRead(Integer blogRead) {
        this.blogRead = blogRead;
    }

    public Integer getBlogCollect() {
        return blogCollect;
    }

    public void setBlogCollect(Integer blogCollect) {
        this.blogCollect = blogCollect;
    }

    public Integer getBlogComment() {
        return blogComment;
    }

    public void setBlogComment(Integer blogComment) {
        this.blogComment = blogComment;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
        return this.blogId;
    }
    }