package com.songsmily.petapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.songsmily.petapi.dto.BasePage;
import com.songsmily.petapi.entity.BlBlog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 博客表(BlBlog)表服务接口
 *
 * @author SongSmily
 * @since 2020-04-06 11:01:31
 */
public interface BlBlogService extends IService<BlBlog> {

    String postBlogImage(MultipartFile image);

    boolean deleteBlogImage(String imageUrl);

    boolean addBlog(BlBlog blBlog);

    BasePage<List> getBlogInType(Integer pageSize, Integer currentPage, Integer activeType);

    Map<String, Object> getBlogInfoById(String blogId);
}