package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-13
 */
public interface EduChapterService extends IService<EduChapter> {


    //根据课程id查询章节和小节
    List<ChapterVo> getChapterVideoById(String id);

    //如果章节里面有小节不能删除
    void removeChapterById(String id);
}
