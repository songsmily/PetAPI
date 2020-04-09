package com.songsmily.petapi.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * 收藏时间(BlCollect)表实体类
 *
 * @author SongSmily
 * @since 2020-04-09 09:26:36
 */
@SuppressWarnings("serial")
public class BlCollect extends Model<BlCollect> implements Serializable {
    //收藏id
    private String collectionId;
    //帖子id
    private String blogId;
    //用户id
    private Integer userId;
    //收藏时间
    private Long collectionTime;

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Long collectionTime) {
        this.collectionTime = collectionTime;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.collectionId;
    }
    }