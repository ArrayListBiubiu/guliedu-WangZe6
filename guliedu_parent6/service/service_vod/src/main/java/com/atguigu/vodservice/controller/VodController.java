package com.atguigu.vodservice.controller;


import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.handler.GuliException;
import com.atguigu.vodservice.utils.DefaultAcsClientVod;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/eduvod/vod")
@CrossOrigin
public class VodController {

    /*
        上传视频的方法
     */
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename(); //需要上传的文件名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));  //在阿里云中显示的文件名称
            InputStream inputStream = file.getInputStream(); //文件流

            //获取request
            UploadStreamRequest request =
                    new UploadStreamRequest("LTAI4FkV6Dj9cqqH5QbVKcWu", "pxiYpwP0pKir4xnow9sXX2kzqEoOl5",
                            title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            //获取response
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = "";
            if (response.isSuccess()) {
                videoId = response.getVideoId();    //获取视频id
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return R.ok().data("videoId",videoId);
        }catch(Exception e) {
            throw new GuliException(20001,"上传失败");
        }
    }


    /*
        根据视频id删除单个视频
     */
    @DeleteMapping("{videoId}")
    public R deleteVideoAliyun(@PathVariable String videoId) {
        try{
            //1 创建初始化对象
            DefaultAcsClient client =
                    DefaultAcsClientVod.initVodClient("LTAI4FkV6Dj9cqqH5QbVKcWu", "pxiYpwP0pKir4xnow9sXX2kzqEoOl5");
            //2 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //3 向request设置视频id
            request.setVideoIds(videoId);
            //4 调用初始化对象的方法
            client.getAcsResponse(request);
            return R.ok();
        }catch(Exception e){
            return R.error();
        }
    }

    /*
        删除多个视频
     */
    @DeleteMapping("deleteMoreVideo")
    public R deleteMoreVideo(@RequestParam("videoIdList") List videoIdList) {
        try{
            //1 创建初始化对象
            DefaultAcsClient client =
                    DefaultAcsClientVod.initVodClient("LTAIq6nIPY09VROj",
                                                    "FQ7UcixT9wEqMv9F35nORPqKr8XkTF");

            //2 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //3 向request设置视频id
            // 将 videoIdList 变成  11,12,13
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(videoIds);

            //4 调用初始化对象的方法
            client.getAcsResponse(request);
            return R.ok();
        }catch(Exception e){
            return R.error();
        }
    }
}
