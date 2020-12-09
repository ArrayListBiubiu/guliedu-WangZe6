package com.atguigu.ekservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer     //Eureka所需注解
public class EkApplication {

    public static void main(String[] args) {

        SpringApplication.run(EkApplication.class, args);

    }

}
