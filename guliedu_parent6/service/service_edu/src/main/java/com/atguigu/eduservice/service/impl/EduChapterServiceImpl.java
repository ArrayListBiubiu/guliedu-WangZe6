package com.atguigu.eduservice.service.impl;


import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.entity.vo.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-12-28
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    //根据课程id查询章节和小节
    @Override
    public List<ChapterVo> getChapterVideoById(String id) {
        //1.获取“edu_chapter”表中与“course_id”对应的章节记录
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",id);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);


        //2.获取“edu_video”表中与“course_id”对应的小节记录
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",id);
        List<EduVideo> videoList = videoService.list(wrapperVideo);

        //创建list集合用于最终数据封装（章节的list集合）
        List<ChapterVo> finalList = new ArrayList<>();

        //3.抽取章节中的信息（id、title）封装到“章节vo类”中
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);  //遍历
            ChapterVo chapterVo = new ChapterVo();  //创建目标对象
            BeanUtils.copyProperties(eduChapter, chapterVo); //转换

            finalList.add(chapterVo);

            //创建list集合用于封装小节
            List<VideoVo> videoVos = new ArrayList<>();

            //4.抽取小节中的信息（id、title）封装到“小节vo类”中
            /*
                有一个前提条件
                类似于，二级分类的parent_id=一级分类的id
                所以，小节的chapter_id=章节的id
             */
            for (int m = 0; m < videoList.size(); m++) {
                EduVideo eduVideo = videoList.get(m);   //遍历
                if (eduVideo.getChapterId().equals((eduChapter.getId()))) {
                    VideoVo videoVo = new VideoVo();    //创建目标对象
                    BeanUtils.copyProperties(eduVideo, videoVo); //转换
                    videoVos.add(videoVo);
                }
            }
            //把封装之后小节放到每个章节里面
            chapterVo.setChildren(videoVos);
        }
        return finalList;
    }

    /*
        删除章节
            注：需要判断是否有小节，有的话不能删除
     */
    @Override
    public void removeChapterById(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        int count = videoService.count(wrapper);
        if(count == 0) {
            baseMapper.deleteById(id);  //没有小节,删除章节
        } else {
            throw new GuliException(20001,"不能删除");  //有小节，不能删除
        }
    }
}
