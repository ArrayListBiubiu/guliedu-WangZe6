package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.Banner;
import com.atguigu.cmsservice.service.BannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 前台轮播图的管理（增删改查）
 */
@RestController
@RequestMapping("/cmsservice/banner")
public class BannerController {

    @Autowired
    BannerService bannerService;

    // 添加
    @PostMapping("addBanner")
    public R addBanner(@RequestBody Banner banner) {
        bannerService.saveBanner(banner);
        return R.ok();
    }

    //修改
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody Banner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    //根据id查询
    @GetMapping("{id}")
    public R getBanner(@PathVariable String id) {
        Banner banner = bannerService.getById(id);
        return R.ok().data("banner", banner);
    }

    //分页查询
    @GetMapping("getBannerList/{current}/{limit}")
    public R getBannerList(@PathVariable long current, @PathVariable long limit) {
        Page<Banner> page = new Page<>();
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //把分页所有数据封装到page对象里面
        bannerService.page(page, wrapper);
        return R.ok().data("total",page.getTotal()).data("rows",page.getRecords());
    }
}

