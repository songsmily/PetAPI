package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.BlTagDao;
import com.songsmily.petapi.entity.BlTag;
import com.songsmily.petapi.service.BlTagService;
import com.songsmily.petapi.service.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * (BlTag)表服务实现类
 *
 * @author SongSmily
 * @since 2020-04-10 14:22:55
 */
@Service("blTagService")
public class BlTagServiceImpl extends ServiceImpl<BlTagDao, BlTag> implements BlTagService {
    @Resource
    BlTagDao blTagDao;
    @Resource
    RedisService redisService;

    /**
     * 查询所有标签 先查询Redis 如果redis没有 查询数据库并存入redis
     * @return
     */
    @Override
    public List<BlTag> getTags() {
        //获取redis数据
        List<BlTag> blTags = redisService.getBlogTags();
        //判断redis数据是否为空
        if (blTags.size() < 1) {
            blTags = blTagDao.selectList(null);
            redisService.setBlogTags(blTags);
        }
        return blTags;
    }

    /**
     * 新增标签 思路 向数据库插入标签 插入 成功后 清空redis中数据 调用getTags方法更新Redis中数据
     * @param blTag
     * @return
     */
    @Override
    public boolean addTag(BlTag blTag) {
        int insert = blTagDao.insert(blTag);
        if (insert > 0) {
            //清空redis数据
            redisService.clearBlogTag();

            getTags();
        }
        return insert > 0;
    }

    @Override
    public List<BlTag> getHotTags() {
        List<BlTag> hotTags = redisService.getHotTags();
        Collections.sort(hotTags, new Comparator<BlTag>() {
            @Override
            public int compare(BlTag o1, BlTag o2) {
                return o2.getTagCount().compareTo(o1.getTagCount());
            }
        });
        if (hotTags.size() >  20) {
            hotTags  = hotTags.subList(1, 20);
        }
        return hotTags;
    }

}