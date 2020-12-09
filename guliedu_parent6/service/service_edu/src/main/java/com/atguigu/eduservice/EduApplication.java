package com.atguigu.eduservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * 微服务调用：
 *  1.  在vod中写删除的接口（方法）
 *  2.  在edu中调用接口
 *      2.1.    在启动类添加注解 @EnableFeignClients //服务调用
 *      2.2.    创建接口VodClient，在接口添加注解指定调用服务的名字 @FeignClient(value = "service-vod", fallback = VodFileDegradeFeignClient.class)
 *      2.3.    在controller中调用接口（实质上是通过这个接口，进而调用vod的删除接口）
 */
@SpringBootApplication
@EnableEurekaClient //服务注册，与“EkApplication”的@EnableEurekaServer呼应
@EnableFeignClients //服务调用
@ComponentScan({"com.atguigu"}) //扫描所有模块的“com.atguigu"下文件
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}

