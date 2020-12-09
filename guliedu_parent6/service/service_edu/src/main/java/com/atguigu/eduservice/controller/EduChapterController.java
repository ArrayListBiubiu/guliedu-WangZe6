package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //根据课程id查询章节和小节
    @GetMapping("getAllChapterVideoId/{id}")
    public R getAllChapterVideoId(@PathVariable String id) {
        List<ChapterVo> list = chapterService.getChapterVideoById(id);
        return R.ok().data("allChapterVideo", list);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return R.ok();
    }

    /*
        删除章节
            注：不能简单的使用：chapterService.removeChapterById(id)，因为章节中有小节需要额外处理
        处理方案：1.有小节时不能直接删除章节，提示“需要先删除小节，才能删除章节”
                 2.直接删除章节和小节
        此处采用第一种方案
     */
    @DeleteMapping("{id}")
    public R deleteChapter(@PathVariable String id) {
        chapterService.removeChapterById(id);
        return R.ok();
    }

    //根据id查询章节
    @GetMapping("{id}")
    public R getChapterInfo(@PathVariable String id) {
        EduChapter eduChapter = chapterService.getById(id);
        return R.ok().data("chapter",eduChapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }
}

