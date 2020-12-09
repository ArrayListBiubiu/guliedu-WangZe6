package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.QueryTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * 在mp中，service和mapper相应实现方法已经疯转完毕，即只需要专注写controller部分即可
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    EduTeacherService teacherService;

    /*
        查询所以讲师
     */
    @GetMapping
    public R getAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }


    /*
        删除讲师
        http://localhost:8011/eduservice/teacher/id
     */
    @DeleteMapping("{id}")
    public R deleteTeacherId(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /*
        分页查询（不带条件）
            current 当前页
            limit 每页记录数
     */
    @GetMapping("findTeacherPage/{current}/{limit}")
    public R getTeacherPage(@PathVariable long current,
                            @PathVariable long limit) {
        //1.创建page对象
        Page<EduTeacher> page = new Page<>(current,limit);
        //2.分页查询后，将所有的数据封装进page对象中
        teacherService.page(page, null);
        //3.从page对象中获取所需数据
        long total = page.getTotal();   //总记录数
        List<EduTeacher> records = page.getRecords();   //每页记录的对象集合
        System.out.println(records);
        //5.返回数据
        return R.ok().data("total", total).data("rows", records);
    }


    /*
        分页查询（带条件）
            current 当前页
            limit 每页记录数
        @RequestBody表示使用json形式传递数据，并把json数据封装到对象中
            注：必须使用Post提交方式（使用get得不到"queryTeacher"的值会报空指针异常，其他方式得到的值不全）
        required = false
            从后台提交的参数中可以为空
     */
    @PostMapping("findTeacherPageCondition/{current}/{limit}")
    public R getTeacherPageCondition(@PathVariable long current,
                            @PathVariable long limit,
                            @RequestBody(required = false) QueryTeacher queryTeacher) {
        //1 创建page对象
        Page<EduTeacher> page = new Page<>(current,limit);
        //2.创建wrapper对象，用于封装条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //判断条件值是否为空
        String name = queryTeacher.getName();
        Integer level = queryTeacher.getLevel();
        String begin = queryTeacher.getBegin();
        String end = queryTeacher.getEnd();

        if(!StringUtils.isEmpty(name)) {    //name值不为空
            //拼接条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {   //level值不为空
            //拼接条件
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {   //begin值不为空
            //拼接条件
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {     //end值不为空
            //拼接条件
            wrapper.le("gmt_create",end);
        }

        //3.分页查询后，将数据封装进page对象中
        teacherService.page(page, wrapper);
        //4.从page对象中获取所需数据
        long total = page.getTotal();   //总记录数
        List<EduTeacher> records = page.getRecords();   //每页记录的对象集合

        //5.返回数据
        return R.ok().data("total",total).data("rows",records);

    }


    /*
        讲师添加功能
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /*
        根据id查询讲师
     */
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable Integer id) {
        EduTeacher eduteacher = teacherService.getById(id);
        return R.ok().data("eduTeacher", eduteacher);
    }


    /*
        修改讲师
     */
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}

