package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (BlTag)表实体类
 *
 * @author SongSmily
 * @since 2020-04-10 14:22:55
 */
@SuppressWarnings("serial")
public class BlTag extends Model<BlTag> implements Serializable{
    @TableId(value = "tag_id", type = IdType.AUTO)
    //tagID
    private Integer tagId;
    //标签名
    private String tagName;
    @TableField(exist = false)
    //标签总数
    private Integer tagCount;

    public BlTag(Integer tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public BlTag() {
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTagCount() {
        return tagCount;
    }

    public void setTagCount(Integer tagCount) {
        this.tagCount = tagCount;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.tagId;
    }
    }