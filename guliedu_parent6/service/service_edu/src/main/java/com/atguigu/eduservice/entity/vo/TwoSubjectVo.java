package com.atguigu.eduservice.entity.vo;

import lombok.Data;


/**
 * 封装树形结构图：二级分类
 *
 * 因为
 * vue所需代码部分为：id、label、children
 * 所以
 * 成员变量设置为：id、title、children
 */
@Data
public class TwoSubjectVo {

    private String id;      //id
    private String title;   //名称
}
