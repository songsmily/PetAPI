package com.songsmily.petapi.service;

import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.dto.BlogSelectParams;
import com.songsmily.petapi.dto.CommentSelectParams;

public interface BlCheckService {

    BasePage getBlogs(BlogSelectParams params);

    boolean doBlogPass(String blogId);

    boolean doBlogDelete(String blogId);

    BasePage getComments(CommentSelectParams params);

    boolean doCommentPass(String commentId);

    boolean doCommentDelete(String commentId);

    void doCommentSecDelete(String commentSecId);

    boolean doCommentSecPass(String commentSecId);
}
