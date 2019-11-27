package com.songsmily.petapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("com.songsmily.petapi.dao")
public class PetApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetApiApplication.class, args);
    }

}
