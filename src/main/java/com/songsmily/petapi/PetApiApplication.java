package com.songsmily.petapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;

@SpringBootApplication
@MapperScan("com.songsmily.petapi.dao")
@EnableTransactionManagement(proxyTargetClass = true)
public class PetApiApplication {

    public static void main(String[] args) {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(DataSize.parse("1024000KB")); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize(DataSize.parse("1024000KB"));

        SpringApplication.run(PetApiApplication.class, args);
    }

}
