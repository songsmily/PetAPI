package com.songsmily.petapi.service;

import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Article;
import com.songsmily.petapi.utils.PageInfo;
import com.songsmily.petapi.utils.PageQuery;
import org.springframework.stereotype.Service;

public interface ArticleService {

    Result<Article> create(Article article);

    PageInfo<Article> list(PageQuery pageQuery);

    boolean likeArticle(Long articleId);
}
