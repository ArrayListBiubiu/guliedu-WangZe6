package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseConfirmInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-13
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据课程id查询课程确认信息
    CourseConfirmInfo getCourseConfirm(String id);
}
