package com.atguigu.ossservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)     //禁止加载数据库信息
@ComponentScan({"com.atguigu"})
public class OssApplication {

    public static void main(String[] args) {

        SpringApplication.run(OssApplication.class, args);

    }

}
