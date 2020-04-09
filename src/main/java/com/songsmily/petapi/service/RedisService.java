package com.songsmily.petapi.service;

import com.songsmily.petapi.entity.BlCollect;

public interface RedisService {
    boolean increBlogRead(String blogId);

    Integer getBlogGoodCount(String blogId);

    boolean getIsBlogGood(String blogId);

    boolean addBlogConnect(BlCollect collect);

    boolean removeBlogConnect(BlCollect collect);

    boolean getIsBlogConnect(String blogId);

    Integer getBlogConnectCount(String blogId);

    boolean addBlogGood(String blogId);

    boolean removeBlogGood(String blogId);
}
