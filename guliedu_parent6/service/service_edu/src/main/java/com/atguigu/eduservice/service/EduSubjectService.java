package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.vo.OneSubjectVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-12-25
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    List<String> importSubjectData(MultipartFile file);

    List<OneSubjectVo> getAllSubject();
}
