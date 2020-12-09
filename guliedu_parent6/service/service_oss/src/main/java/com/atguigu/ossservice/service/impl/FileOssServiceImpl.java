package com.atguigu.ossservice.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.ossservice.service.FileOssService;
import com.atguigu.ossservice.utils.ConstantPropertiesUtil;
import com.atguigu.servicebase.handler.GuliException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;


@Service
public class FileOssServiceImpl implements FileOssService {

    @Override
    public String uploadFileOss(MultipartFile file) {

        //配置文件 -> 工具类 -> service：获取地域节点、id、秘钥、bucketname
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流：将文件信息转化为流信息
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();

            //防止文件重名发生覆盖现象
            String uuid = UUID.randomUUID().toString();
            fileName = uuid+fileName;

            //实现按照日期分类存储（创建多级路径，会在OSS自动穿件相应目录）
            /*
                使用“new DateTime().toString("yyyy/MM/dd")”功能需要导入依赖
                <!-- 日期工具栏依赖 -->
                <dependency>
                    <groupId>joda-time</groupId>
                    <artifactId>joda-time</artifactId>
                    <version>2.10.1</version>
                </dependency>
             */
            String path = new DateTime().toString("yyyy/MM/dd");  // 2019/12/25
            fileName = path+"/"+fileName;

            //实现上传
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            //拼接上传文件url
            //真实返回模板格式：https://guli-filetest0826.oss-cn-beijing.aliyuncs.com/1.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        }catch(Exception e) {
            throw new GuliException(20001,"上传失败");
        }
    }
}
