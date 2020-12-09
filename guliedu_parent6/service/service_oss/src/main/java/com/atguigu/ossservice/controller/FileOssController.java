package com.atguigu.ossservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.ossservice.service.FileOssService;
import com.atguigu.ossservice.utils.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
public class FileOssController {

    @Autowired
    FileOssService fileOssService;

    /*
        MultipartFile：直接获取从前端上传过来的文件
            1.获取上传过来的文件
            2.将文件信息移交给service处理，最终获得上传到OSS成功后的文件路径
     */
    @PostMapping("fileUpload")
    public R fileUploadOss(MultipartFile file) {

        String url = fileOssService.uploadFileOss(file);

        // TODO: 2020/1/11 上传成功后，只需要返回结果就可以了：return R.ok();

        System.out.println("222");
        System.out.println(ConstantPropertiesUtil.ACCESS_KEY_ID);
        return R.ok().data("url",url);
    }
}
