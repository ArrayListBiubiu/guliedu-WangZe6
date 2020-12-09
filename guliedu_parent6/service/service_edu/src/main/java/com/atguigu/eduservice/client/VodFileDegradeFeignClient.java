package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 当发生超时时，会调用这个实现类的方法
 *     （1）@Component，交给spring管理
 *     （2）实现接口，做一些发生熔断时简单的输出即可
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {

    @Override
    public R deleteVideoAliyun(String videoId) {
        return R.error();
    }

    @Override
    public R deleteMoreVideo(List videoIdList) {
        return R.error();
    }

}
