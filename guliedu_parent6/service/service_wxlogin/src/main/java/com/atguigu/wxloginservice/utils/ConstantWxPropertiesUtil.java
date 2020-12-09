package com.atguigu.wxloginservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 读取配置文件类
 *      注：1.实现InitializingBean接口，实现afterPropertiesSet()方法
 *          2.添加注解@Component
 */
@Component
public class ConstantWxPropertiesUtil implements InitializingBean {
    /*
        自动读取配置文件中的wx.open.app_id的value值，并赋值给appId
     */
    @Value("${wx.open.app_id}")
    private String appId;
    @Value("${wx.open.app_secret}")
    private String appSecret;
    @Value("${wx.open.redirect_url}")
    private String redirectUrl;
    /*
        声明一些常量，方便其他类读取：类名.属性名
     */
    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;
    /*
        该工具类被调用时，会自动实现afterPropertiesSet()方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }
}
