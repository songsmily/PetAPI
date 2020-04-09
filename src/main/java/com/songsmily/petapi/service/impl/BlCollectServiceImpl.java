package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.BlCollectDao;
import com.songsmily.petapi.entity.BlCollect;
import com.songsmily.petapi.entity.User;
import com.songsmily.petapi.service.BlCollectService;
import com.songsmily.petapi.service.RedisService;
import com.songsmily.petapi.utils.ShiroUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 收藏(BlCollect)表服务实现类
 *
 * @author SongSmily
 * @since 2020-04-09 09:26:36
 */
@Service("blCollectService")
public class BlCollectServiceImpl extends ServiceImpl<BlCollectDao, BlCollect> implements BlCollectService {

    @Resource
    RedisService redisService;

    /**
     * 添加收藏
     * @param collect
     * @return
     */
    @Override
    public boolean addBlogConnect(BlCollect collect) {
        //构建收藏对象
        collect.setCollectionId(UUID.randomUUID().toString());
        collect.setCollectionTime(System.currentTimeMillis());
        collect.setUserId(ShiroUtil.getUser(new User()).getId());

        boolean res = redisService.addBlogConnect(collect);
        return false;
    }

    @Override
    public boolean removeBlogConnect(BlCollect collect) {
        //构建收藏对象
        collect.setCollectionId(UUID.randomUUID().toString());
        collect.setCollectionTime(System.currentTimeMillis());
        collect.setUserId(ShiroUtil.getUser(new User()).getId());

        boolean res = redisService.removeBlogConnect(collect);
        return false;
    }
}