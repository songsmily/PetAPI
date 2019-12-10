package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.MusicDao;
import com.songsmily.petapi.entity.Music;
import com.songsmily.petapi.service.MusicService;
import org.springframework.stereotype.Service;

/**
 * (Music)表服务实现类
 *
 * @author SongSmily
 * @since 2019-12-03 15:24:22
 */
@Service("musicService")
public class MusicServiceImpl extends ServiceImpl<MusicDao, Music> implements MusicService {

}