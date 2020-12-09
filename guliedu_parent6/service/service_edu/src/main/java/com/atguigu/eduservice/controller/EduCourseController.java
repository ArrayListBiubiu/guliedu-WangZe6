package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseConfirmInfo;
import com.atguigu.eduservice.entity.vo.CourseInfoForm;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-12-27
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /*
        添加课程信息：
            注：前端在点击保存时，会将课程信息以json形式传递过来
                所以直接用“@RequestBody CourseInfoForm courseInfoForm”接收对应的json信息
        传递过来的信息是需要往2张表中添加
            表：edu_course、edu_course_description
     */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String courseId = courseService.addCourseInfo(courseInfoForm);
        return R.ok().data("courseId", courseId);
    }

    /*
        根据课程id查询课程信息
     */
    @GetMapping("{courseId}")
    public R getCourseInfoId(@PathVariable String courseId) {
        CourseInfoForm courseInfoForm = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoForm", courseInfoForm);
    }


    /*
        修改课程信息
     */
    @PostMapping("updateCourse")
    public R updateCourse(@RequestBody CourseInfoForm courseInfoForm) {
        courseService.updateCourse(courseInfoForm);
        return R.ok();
    }


    /*
        最终发布页面开发（根据课程id查询课程确认信息）
     */
    @GetMapping("getConfirmCourseInfo/{id}")
    public R getConfirmCourseInfo(@PathVariable String id) {
        CourseConfirmInfo courseConfirmInfo = courseService.getCourseConfirm(id);
        return R.ok().data("courseConfirmInfo", courseConfirmInfo);
    }

    /*
        最终发布页面开发（确认发布）
            注：本质上就是把“edu_course”表中的“status”字段值修改为“Normal”，页面变化由前端完成
     */
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        //修改status值Normal
        //根据课程id查询课程信息
        EduCourse eduCourse = courseService.getById(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    /*
        课程列表开发（查询所有课程信息）
     */
    @GetMapping("getList")
    public R getList() {
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list", list);
    }

    /*
        课程列表开发（删除课程时直接删除该课程下所有章节、小节）
     */
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.deleteCourse(courseId);
        return R.ok();
    }
}

