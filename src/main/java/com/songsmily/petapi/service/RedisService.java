package com.songsmily.petapi.service;

import com.songsmily.petapi.dto.BlogSelectParams;
import com.songsmily.petapi.entity.BlCollect;
import com.songsmily.petapi.entity.BlTag;

import java.util.List;

public interface RedisService {
    boolean increBlogRead(String blogId);

    boolean removeBlogTags(String[] tagArr);

    Integer getBlogGoodCount(String blogId);

    boolean getIsBlogGood(String blogId);

    Integer getBlogReadCount(String blogId);

    boolean addBlogConnect(BlCollect collect);

    boolean removeBlogConnect(BlCollect collect);

    boolean getIsBlogConnect(String blogId);

    Integer getBlogConnectCount(String blogId);

    boolean getIsCommentGood(String blogId);

    boolean addBlogGood(String blogId);

    boolean removeBlogGood(String blogId);

    boolean removeCommentGood(String commentId);

    boolean addCommentGood(String commentId);

    Integer getCommentGoodCount(String commentId);


    List<BlTag> getBlogTags();

    void setBlogTags(List<BlTag> blTags);

    void clearBlogTag();

    boolean addBlogTags(String[] tagArr);

    List<BlTag> getBlogTagsByTagIds(String[] tagIdArr);

    List<BlTag> getHotTags();

    List<BlCollect> getUserCollectBlogIds(BlogSelectParams params);


    Integer getUserCollectBlogTotal();


    boolean removeBlogALl(String blogId);

    boolean removeCommentAll(List<String> commentIds);
}
