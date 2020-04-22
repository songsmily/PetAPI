package com.songsmily.petapi.config;

import com.baidu.aip.contentcensor.AipContentCensor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaiDuPApiConfig {
    private static final String APP_ID ="19323618";

    private static final String API_KEY = "ZGGybTH4gCmD80fkgw0F1Acr";

    private static final String SECRET_KEY = "cvs25U6ztuAcssD6xFj9srzDC7D6SD9P";

    /*初始化客户端*/
    public static final AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);

}
