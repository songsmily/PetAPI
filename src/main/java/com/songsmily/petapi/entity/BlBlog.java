package com.songsmily.petapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 博客表(BlBlog)表实体类
 *
 * @author SongSmily
 * @since 2020-04-06 11:01:31
 */
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
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
    //审核状态 1 管理员已审核 0:等待管理员审核
    private Integer blogStatus;

    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String avatarUrl;
    @TableField(exist = false)
    private Long collectTime;
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