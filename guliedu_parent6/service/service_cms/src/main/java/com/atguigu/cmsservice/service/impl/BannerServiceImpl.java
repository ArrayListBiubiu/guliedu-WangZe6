package com.atguigu.cmsservice.service.impl;


import com.atguigu.cmsservice.entity.Banner;
import com.atguigu.cmsservice.mapper.BannerMapper;
import com.atguigu.cmsservice.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import com.atguigu.cmsservice.mapper.BannerMapper;
import org.springframework.stereotype.Service;


@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    //前台查询banner的方法
    /*
        在Redis数据库中会新生成以banner::selectIndexList为key值的数据
     */
    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<Banner> getAllBanner() {
        List<Banner> list = baseMapper.selectList(null);
        return list;
    }

    /*
        当有key是以“banner”开头的全部清空，重新从数据库中抽取数据
     */
    @CacheEvict(value = "banner", allEntries = true)
    @Override
    public void saveBanner(Banner banner) {
        baseMapper.insert(banner);
    }
}