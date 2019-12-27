package com.songsmily.petapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songsmily.petapi.dao.PettypeDao;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.Pettype;
import com.songsmily.petapi.service.PettypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * (Pettype)表服务实现类
 *
 * @author SongSmily
 * @since 2019-12-20 19:30:28
 */
@Service("pettypeService")
public class PettypeServiceImpl extends ServiceImpl<PettypeDao, Pettype> implements PettypeService {

    @Resource
    PettypeDao pettypeDao;

    @Override
    public Result getPetTypeArray() {
        List<Pettype> pettypes = pettypeDao.selectList(null);
        Set<String> setOne = new HashSet<>();
        Set<String> setTwo = new HashSet<>();

        for (Pettype pettype : pettypes) {
            setOne.add(pettype.getPetClassifyOne());
            setTwo.add(pettype.getPetClassifyTwo());
        }

        List<Map<String, Object>> resultOne = new ArrayList<>();
        for (String itemOne : setOne) {
            Map<String, Object> mapOne = new HashMap<>();
            mapOne.put("label", itemOne);
            mapOne.put("value", 1);
            List<Map<String, Object>> resultTwo = new ArrayList<>();
            for (String itemTwo : setTwo) {
                Map<String, Object> mapTwo = new HashMap<>();
                mapTwo.put("label", itemTwo);
                mapTwo.put("value", 2);
                List<Map<String, Object>> foorThree = new ArrayList<>();
                Boolean pos = false;
                for (Pettype pettypeThree : pettypes) {
                    if (pettypeThree.getPetClassifyTwo().equals(itemTwo) && pettypeThree.getPetClassifyOne().equals(itemOne)) {
                        pos = true;
                        Map<String, Object> mapThree = new HashMap<>();
                        mapThree.put("label", pettypeThree.getPetClassifyThree());
                        mapThree.put("value", pettypeThree.getPetTypeId());
                        foorThree.add(mapThree);
                    }
                }
                if (pos) {
                    mapTwo.put("children", foorThree);
                    resultTwo.add(mapTwo);
                    mapOne.put("children", resultTwo);
                }

            }
            resultOne.add(mapOne);
        }
        return new Result(resultOne);
    }

}