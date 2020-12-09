package com.atguigu.ossservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;


/**
 * 读取配置文件类：
 *      1.项目启动，扫描到“@Component”注解，将“ConstantPropertiesUtil”类交给spring管理（IOC）
 *      2.在IOC过程中由于“@Value”注解，优先完成对成员变量的初始化
 *      3.发现实现了InitializingBean接口，马上运行重写的“afterPropertiesSet()”方法
 *      4.最终实现，将私有成员变量的值赋值给“public static”修饰的供外部使用的变量入口
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    //@Value作用：读取配置文件，将“aliyun.oss.file.endpoint”对应的value值赋值给“endpoint”变量中
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyid;
    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    //设置为静态常量，方便service读取
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
        BUCKET_NAME = bucketname;
    }

}

