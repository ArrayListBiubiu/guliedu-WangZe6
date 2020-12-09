package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.OneSubjectVo;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-12-25
 */


@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;


    //需求：按照树形结构返回信息（返回所有分类 ，按照固定格式）
    @GetMapping
    public R getAllSubject() {
        List<OneSubjectVo> allSubject = subjectService.getAllSubject();
        return R.ok().data("allSubject",allSubject);
    }


    //添加课程分类
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //1 获取上传的excel文件 MultipartFile
        /*
            优化addSubject()方法：
                当excel表格中某一行出现空值，跳过此行继续执行，并将跳过的行信息返回
         */
        List<String> msgList = subjectService.importSubjectData(file);
        if (msgList.size() == 0) {
            return R.ok();
        } else {
            return R.error().data("msgList", msgList);
        }
    }

}

