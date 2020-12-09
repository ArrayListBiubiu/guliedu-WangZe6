package com.atguigu.eduservice.client;


import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


/**
 * 在“service_edu”中只需要声明接口，也可以理解为，实质上该接口的实现类就是“service_vod”模块中的VodController类中的方法（即，远程调用）
 */

/*
    @FeignClient -- 服务调用专用注解
    （1）value = "service-vod"
        注：value的参数是，“service-vod”模块中服务名（配置文件中的“spring.application.name=service-vod”）
    （2）fallback = VodFileDegradeFeignClient.class
        注：这个参数是在开启熔断机制的时候添加，意思是在发生超时时候调用什么方法
 */
@FeignClient(value = "service-vod", fallback = VodFileDegradeFeignClient.class) //声明被调用的服务名
@Component  //交给spring管理
public interface VodClient {

    /*
        声明需要调用的方法
            注：@DeleteMapping("?")中的参数值是全路径名
            注：@PathVariable()，必须有参数，否则报错，比较特殊
     */
    @DeleteMapping("/eduvod/vod/{videoId}")
    public R deleteVideoAliyun(@PathVariable("videoId") String videoId);


    //删除多个视频
    @DeleteMapping("/eduvod/vod/deleteMoreVideo")
    public R deleteMoreVideo(@RequestParam("videoIdList") List videoIdList);

}
