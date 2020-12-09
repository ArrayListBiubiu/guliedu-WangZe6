package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseConfirmInfo;
import com.atguigu.eduservice.entity.vo.CourseInfoForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-13
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfo(String courseId);

    void updateCourse(CourseInfoForm courseInfoForm);

    CourseConfirmInfo getCourseConfirm(String id);

    //删除课程
    void deleteCourse(String courseId);
}
