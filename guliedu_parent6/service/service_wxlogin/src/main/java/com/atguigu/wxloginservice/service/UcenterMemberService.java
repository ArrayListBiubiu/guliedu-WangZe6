package com.atguigu.wxloginservice.service;

import com.atguigu.wxloginservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-07
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //判断数据库表是否存在相同的微信数据openid
    UcenterMember getWxInfoByOpenid(String openid);
}
