package com.songsmily.petapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.internal.OSSUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.songsmily.petapi.dao.PettypeDao;
import com.songsmily.petapi.dao.PlotDao;
import com.songsmily.petapi.entity.Pettype;
import com.songsmily.petapi.service.impl.MailServiceImpl;
import com.songsmily.petapi.utils.OssUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
class PetApiApplicationTests {

    @Autowired
    MailServiceImpl mailService;
    @Autowired
    private TemplateEngine templateEngine;
    @Resource
    PlotDao plotDao;
    @Resource
    OssUtil ossUtils;
    @Resource
    PettypeDao pettypeDao;

    @Test
    public void testPet(){
        List<Pettype> pettypes = pettypeDao.selectList(null);
        Set<String> setOne = new HashSet<>();
        Set<String> setTwo = new HashSet<>();

        for (Pettype pettype:pettypes) {
            setOne.add(pettype.getPetClassifyOne());
            setTwo.add(pettype.getPetClassifyTwo());
        }
        List<String> list = new ArrayList<>(setTwo);
        list.sort(null);
        System.err.println(list);
        System.err.println(setTwo);

        List<Map<String,Object>> resultOne = new ArrayList<>();
        for (String itemOne : setOne){
            Map<String, Object> mapOne = new HashMap<>();
            mapOne.put("label", itemOne);
            mapOne.put("value", 1);
            List<Map<String,Object>> resultTwo = new ArrayList<>();
            for (String itemTwo :setTwo){
                Map<String, Object> mapTwo = new HashMap<>();
                mapTwo.put("label", itemTwo);
                mapTwo.put("value", 2);
                List<Map<String,Object>> foorThree = new ArrayList<>();
                Boolean pos = false;
                for (Pettype pettypeThree:pettypes) {
                    if (pettypeThree.getPetClassifyTwo().equals(itemTwo) && pettypeThree.getPetClassifyOne().equals(itemOne)){
                        pos = true;
                        Map<String, Object> mapThree = new HashMap<>();
                        mapThree.put("label", pettypeThree.getPetClassifyThree());
                        mapThree.put("value", pettypeThree.getPetTypeId());
                        foorThree.add(mapThree);
                    }
                }
                if (pos){
                    mapTwo.put("children",foorThree);
                    resultTwo.add(mapTwo);
                    mapOne.put("children",resultTwo);
                }

            }
            resultOne.add(mapOne);
        }


        System.err.println(JSONObject.toJSONString( resultOne));
    }
    @Test
    public  void testOss(){
        String str = "http://oss.songsmily.cn/images/1576794405596.jpeg";
        str = str.substring(str.lastIndexOf("images/"));
        System.err.println(str);
//
//        String filename = "images/1576794099160.jpeg";
//        List<String> fileNames = new ArrayList<>();
//        fileNames.add("images/01.jpg");
//        fileNames.add("images/02.jpg");
//        fileNames.add("images/03.jpg");
//        fileNames.add("images/04.jpg");
//        fileNames.add("images/05.jpg");
//        fileNames.add("images/06.jpg");
//        fileNames.add("images/07.jpg");
//        fileNames.add("images/08.jpg");
//        fileNames.add("images/09.jpg");
//        fileNames.add("images/010.jpg");
//        fileNames.add("images/011.jpg");
//        fileNames.add("images/012.jpg");
//        ossUtils.deleteFile20SS(fileNames);
    }

    @Test
    public void testDao(){
        Object json =  JSONObject.toJSON(plotDao.queryPlotAndBuild());
        System.err.println(json);
    }
    @Test
    public void test()
    {
        mailService.sendSimpleMail("2874643810@qq.com","测试","测试");
    }
    @Test
    public void testTemplateMail() {
        //向Thymeleaf模板传值，并解析成字符串
        Context context = new Context();
        context.setVariable("id", "001");
        String emailContent = templateEngine.process("successMail", context);

        mailService.sendHtmlMail("2874643810@qq.com", "注册确认信息", emailContent);
    }


}
