package com.songsmily.petapi;

import com.baidu.aip.contentcensor.AipContentCensor;
import com.songsmily.petapi.config.BaiDuPApiConfig;
import com.songsmily.petapi.utils.CommonUtils;
import com.songsmily.petapi.utils.OssUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetApiApplicationTests {

    @Resource
    RedisTemplate redisTemplate;
    @Resource
    OssUtil ossUtil;

    @Test
    public void test3() throws JSONException {
        JSONObject res = BaiDuPApiConfig.client.textCensorUserDefined("中新网11月28日电据澳洲网报道，澳大利亚宠物保险公司近日公布了2019年澳大利亚最受欢迎宠物名。其中，英国王子幼子的名字阿尔奇成为了最受欢迎雄性狗狗名字的第一名，而洛基成为了最受欢迎雄性猫咪名字的第一名。最受欢迎雌性狗狗和猫咪名字的第一名则全部被露娜包揽。\n" +
                "\n" +
                "王室宝宝名字受欢迎\n" +
                "\n" +
                "据报道，澳大利亚宠物保险公司近日在研究了全澳15万只新投保宠物的数据后，总结出2019年最受欢迎宠物名字排行榜。根据榜单显示，最受欢迎宠物名字主要来源于英国王室宝宝和迪斯尼经典。流行文化激发了人们的想象力，其中英国哈里王子和梅根的幼子阿尔奇的名字成为了最受欢迎雄性宠物狗名字第一位和最受欢迎雄性宠物猫名字第二名。\n" +
                "\n" +
                "人类名字也被广泛应用于宠物名字上，其中有大量的雄性宠物狗叫查理，雌性宠物猫叫Chloe、Poppy和Mia。\n" +
                "\n" +
                "“露娜”蝉联第一名桂冠\n" +
                "\n" +
                "露娜连续第二年获得了澳大利亚最受欢迎雌性宠物猫名字第一名，而第一次上榜的名字包括受到食物启发的名字奥利奥，和迪斯尼主题的名字娜娜。\n" +
                "\n" +
                "受欢迎的雌性宠物狗的名字一般由4个字母组成，如Coco、Ruby和Lola。而以-y和-ie结尾的名字则在雄性宠物中比较常见，如Buddy、Teddy和Bailey以及Alfie和Ollie。\n" +
                "\n" +
                "今年新增加的宠物狗名字包括Leo、Millie和Frankie。\n" +
                "\n" +
                "澳大利亚宠物保险公司发言人克里顿表示，2019年是“王室狂热”的一年，而传统的名字也继续占据主导地位。\n" +
                "\n" +
                "\n" +
                "本文转自有宠网：http://www.yc.cn/news/news-41463.html");
        System.err.println(res);
        System.err.println(res.getString("conclusion"));
//        redisTemplate.opsForValue().decrement("filter:Blog:Count",1);
//        System.err.println((System.currentTimeMillis() - 1586939588687L) / (1000 * 60));
    }

    @Test
    public void test2(){
        /**
         * ![1576810814361.jpeg](http://oss.songsmily.cn/images/1586770586704.1576810814361.jpeg)
         * # 一级标题
         * # 一级标题
         * # 一级标题# 一级标题# 一级标题
         * # 一级标题
         * # 一级标题
         * # 一级标题
         * # 一级标题
         */
        String str = "![1576810814361.jpeg](http://oss.songsmily.cn/images/1586770586704.1576810814361.jpeg)\n" +
                "# 一级标题\n" +
                "# 一级标题\n" +
                "# 一级标题# 一级标题# 一级标题\n" +
                "# 一级标题\n" +
                "# 一级标题\n" +
                "# 一级标题\n" +
                "# 一级标题" +
                "![1576810814361.jpeg](http://oss.songsmily.cn/images/1586770586704.1576810814361.jpeg)\n" +
                "# 一级标题\n" +
                "# 一级标题\n" +
                "# 一级标题# 一级标题# 一级标题\n" +
                "# 一级标题\n" +
                "# 一级标题\n" +
                "# 一级标题\n" +
                "# 一级标题";
        List<String> files = new ArrayList<>();
        String sub = "";
        while (true) {
            if (str.indexOf("http://oss.songsmily.cn/images/") != -1) {
                str = str.substring(str.indexOf("http://oss.songsmily.cn/images/"));
                System.out.println(str.substring(0,str.indexOf(")")));

            } else {
                break;
            }
        }
//        System.err.println("http://oss.songsmily.cn/images/".length());
//        System.out.println(str.substring(str.indexOf("http://oss.songsmily.cn/images/faf") + 31));

    }
    @Test
    public void test(){
        Set keys = redisTemplate.keys("*f62556d7-f416-47ab-bf90-d00ef482b7d4*");
        Long delete = redisTemplate.delete(keys);
        System.err.println(delete);
        Object[] objects = keys.toArray();
        for (int i = 0; i < objects.length; i++) {
            System.err.println(objects[i]);
        }
    }

}
