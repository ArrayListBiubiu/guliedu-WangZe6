package com.atguigu.wxloginservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.atguigu"})
@SpringBootApplication
@MapperScan("com.atguigu.wxloginservice.mapper")
public class WxLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxLoginApplication.class, args);
    }

}
