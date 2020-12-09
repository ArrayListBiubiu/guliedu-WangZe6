package com.atguigu.cmsservice.api;


import com.atguigu.cmsservice.entity.Banner;
import com.atguigu.cmsservice.service.BannerService;
import com.atguigu.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;




/**
 * 轮播图开发（用户端）：
 * 可以发现对于用户端和管理员端各拥有1个查询的controller，其实可以统一使用，但是按照格式规范，
 * 需要将用户端和管理员端分开，而对于管理员，拥有增删改查功能，而对于用户端只有查询功能。
 */
@RestController
@RequestMapping("/cmsservice/banner")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private BannerService bannerService;

    //前台查询banner的方法
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<Banner> list = bannerService.getAllBanner();
        return R.ok().data("list",list);
    }
}

