package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-13
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }


    /*
        删除小节
            注：不同于“删除章节”，不需要额外判断是否需要删除
            注：这里是先根据“视频id”字段的信息，删除阿里云中的视频，再删除表中的整条记录
                “视频id”字段存储的是阿里云中的“视频id”
                所以，如果先删除表中的记录，只是删除了小节信息，和存储在阿里云的“视频id”而已，阿里云中的视频依然存在
     */
    // TODO 删除小节，需要删除阿里云对应的视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {

        EduVideo eduVideo = videoService.getById(id);   //获取小节对象
        String videoSourceId = eduVideo.getVideoSourceId(); //通过小节对象获取视频id
        vodClient.deleteVideoAliyun(videoSourceId); //删除视频

        if(!StringUtils.isEmpty(videoSourceId)) {   //如果视频id不存在，则不需要删除
            vodClient.deleteVideoAliyun(videoSourceId);
        }

        videoService.removeById(id);
        return R.ok();
    }

    //根据id查询小节
    @GetMapping("{id}")
    public R getVideoInfo(@PathVariable String id) {
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("video",eduVideo);
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo video) {
        videoService.updateById(video);
        return R.ok();
    }
}

