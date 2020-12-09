package com.atguigu.eduservice.api;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.management.Query;
import java.util.List;


/**
 * 首页“热门课程”、“名师大咖”的开发
 *      注： 对于前台部分，均采用管理员端的增删改查和用户端的查找，2部分开发方式
 *          （即，查找的接口有2个，一个面向管理员，一个面向用户端）
 */
@RestController
@RequestMapping("/eduservice/index")
@CrossOrigin
public class IndexController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //1 查询8条课程，查询4条名师，在此为了方便sql语句的实现写在了controller中
    @GetMapping
    public R getIndexData() {

        //1 查询前8条热门课程
        //SELECT * FROM edu_course ec ORDER BY id DESC LIMIT 8
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        //根据id降序
        wrapperCourse.orderByDesc("id");
        //查询8条记录
        wrapperCourse.last("limit 8");  //sql语句拼接的方式，可能出现sql注入的问题
        List<EduCourse> eduCourselist = courseService.list(wrapperCourse);

        //2 查询前4个名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> eduTeacherlist = teacherService.list(wrapperTeacher);

        return R.ok().data("hotCourse",eduCourselist).data("teacher",eduTeacherlist);
    }
}
