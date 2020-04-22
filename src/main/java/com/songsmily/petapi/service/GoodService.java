package com.songsmily.petapi.service;

public interface GoodService {
    boolean addBlogGood(String blogId);

    boolean removeBlogGood(String blogId);

    boolean removeCommentGood(String commentId);

    boolean addCommentGood(String commentId);
}
