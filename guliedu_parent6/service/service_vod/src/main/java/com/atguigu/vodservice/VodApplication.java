package com.atguigu.vodservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //vod是视频上传模块，不需要数据库信息
@ComponentScan("com.atguigu")
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
