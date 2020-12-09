package com.atguigu.cmsservice.service;

import com.atguigu.cmsservice.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-15
 */
public interface BannerService extends IService<Banner> {

    //前台查询banner的方法
    List<Banner> getAllBanner();

    //添加
    void saveBanner(Banner banner);
}
