package com.atguigu.eduservice.entity.vo;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 封装树形结构图：一级分类
 *
 * 因为
 * vue所需代码部分为：id、label、children
 * 所以
 * 成员变量设置为：id、title、children（实质上封装的仍然是EduSubject类中抽取的id、title）
 */
@Data
public class OneSubjectVo {

    private String id;      //id
    private String title;   //名称

    private List<TwoSubjectVo> children = new ArrayList<>();
}
