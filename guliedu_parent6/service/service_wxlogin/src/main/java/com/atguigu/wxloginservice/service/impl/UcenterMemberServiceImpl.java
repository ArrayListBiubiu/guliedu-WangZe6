package com.atguigu.wxloginservice.service.impl;

import com.atguigu.wxloginservice.entity.UcenterMember;
import com.atguigu.wxloginservice.mapper.UcenterMemberMapper;
import com.atguigu.wxloginservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-07
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    //判断数据库表是否存在相同的微信数据openid
    @Override
    public UcenterMember getWxInfoByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }
}
