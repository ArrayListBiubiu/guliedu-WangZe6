package com.atguigu.eduservice.entity.vo;

import lombok.Data;

@Data
public class CourseConfirmInfo {
    private String id;
    private String title;   //课程名称
    private String cover;   //课程封面
    private Integer lessonNum;  //课时数
    private String subjectLevelOne; //一级分类
    private String subjectLevelTwo; //二级分类
    private String teacherName; //讲师名
    private String price;//只用于显示
}
