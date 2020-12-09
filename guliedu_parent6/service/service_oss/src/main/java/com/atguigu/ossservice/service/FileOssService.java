package com.atguigu.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileOssService {
    String uploadFileOss(MultipartFile file);
}
