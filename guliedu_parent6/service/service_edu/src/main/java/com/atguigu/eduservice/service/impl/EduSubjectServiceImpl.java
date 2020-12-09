package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.vo.OneSubjectVo;
import com.atguigu.eduservice.entity.vo.TwoSubjectVo;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.reflection.wrapper.BaseWrapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-12-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    //poi读取excel内容
    @Override
    public List<String> importSubjectData(MultipartFile file) {
        try {

            //1.获取文件输入流
            InputStream inputStream = file.getInputStream();

            //2.创建workbook对象
            Workbook workbook = new HSSFWorkbook(inputStream);

            //3.获取sheet
            Sheet sheet = workbook.getSheetAt(0);

            //int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            //获取最后一行的索引值（总共有多少行）
            int lastRowNum = sheet.getLastRowNum();

            /*
                优化1：创建list集合用于封装错误数据
             */
            List<String> msg = new ArrayList<>();

            //从第二行开始遍历
            for (int i = 1; i <= lastRowNum; i++) {
                //4.获取行
                Row row = sheet.getRow(i);

                //5.获取第一列
                Cell cellOne = row.getCell(0);

                /*
                    优化2：第一列为空时，跳过此次循环
                    注：由于可能发生版本兼容性问题，需要判断，cellOne、oneCellValue，两次
                 */
                if(cellOne == null) {
                    //错误提示
                    String error = "第"+(i+1)+"行，第1列数据为空";
                    //放到list集合
                    msg.add(error);
                    //跳过去继续执行
                    continue;
                }

                //获取第一列的内容
                String oneCellValue = cellOne.getStringCellValue();

                /*
                    优化3：第一列内容为空时，跳过此次循环
                 */
                if(StringUtils.isEmpty(oneCellValue)) {
                    //错误提示
                    String error = "第"+(i+1)+"行，第1列数据为空";
                    //放到list集合
                    msg.add(error);
                    //跳过去继续执行
                    continue;
                }

                //添加一级分类
                EduSubject existOneSubject = this.existOneSubject(oneCellValue);
                //没有相同的，则开始添加
                if (existOneSubject == null) {
                    existOneSubject = new EduSubject();
                    //只需要添加，“title”，“parent_id”字段，其他的自动生成
                    existOneSubject.setTitle(oneCellValue);
                    existOneSubject.setParentId("0");
                    baseMapper.insert(existOneSubject);
                }

                //获取一级分类id值
                String pid = existOneSubject.getId();

                //6.获取第二列
                Cell cellTwo = row.getCell(1);

                /*
                    优化4：第二列为空时跳过此次循环
                 */
                if(cellTwo == null) {
                    //错误提示
                    String error = "第"+(i+1)+"行，第2列数据为空";
                    //放到list集合
                    msg.add(error);
                    //跳过去继续执行
                    continue;
                }

                //获取第二列的内容
                String twoCellValue = cellTwo.getStringCellValue();

                /*
                    优化5：判断第二列内容为空时跳过此次循环
                 */
                if(StringUtils.isEmpty(twoCellValue)) {
                    //错误提示
                    String error = "第"+(i+1)+"行，第2列数据为空";
                    //放到list集合
                    msg.add(error);
                    //跳过去继续执行
                    continue;
                }

                //添加二级分类
                EduSubject existTwoSubject = this.existTwoSubject(twoCellValue, pid);
                if (existTwoSubject == null) {
                    existTwoSubject = new EduSubject();
                    existTwoSubject.setTitle(twoCellValue);
                    existTwoSubject.setParentId(pid);
                    baseMapper.insert(existTwoSubject);
                }
            }
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }
    }



    //判断一级分类是否重复
    private EduSubject existOneSubject(String name) {
        //创建QueryWrapper条件封装器对象
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        //添加条件
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }

    //判断二级分类是否重复
    private EduSubject existTwoSubject(String name,String pid) {
        //创建QueryWrapper条件封装器对象
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        //添加条件
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }



    /*
        树形结构图
            注：一直操作的是“edu_subject”数据表
        本质上是将“edu_subject”数据表中的id、title字段信息抽取出来，封装进新的vo类中（一级分类、二级分类）
            注：可以理解为有一个隐含的新的表，其字段为id、title，并且完全来源于“edu_subject”数据表
     */
    @Override
    public List<OneSubjectVo> getAllSubject() {

        //获取“edu_subject”数据表中所有一级分类的记录
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", 0);
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //获取“edu_subject”数据表中所有二级分类的记录
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", 0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建最终需要返回的数据list集合（一级分类）
        List<OneSubjectVo> finalSucjectList = new ArrayList<>();

        /*
            封装一级分类的记录
         */
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject oneSubject = oneSubjectList.get(i);  //获取每一条一级分类的记录
            OneSubjectVo oneSubjectVo = new OneSubjectVo();
            /*
                本质上是创建一个隐含的vo表，其字段对应“edu_subject”数据表中的id、title，并将值赋值给vo表
             */
            //oneSubjectVo.setId(oneSubject.getId());
            //oneSubjectVo.setTitle(oneSubject.getTitle());
            BeanUtils.copyProperties(oneSubject,oneSubjectVo);
            finalSucjectList.add(oneSubjectVo);

            //创建集合，用于封装二级分类
            List<TwoSubjectVo> twoVoList = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                EduSubject twoSubject = twoSubjectList.get(m);  //获取每一条二级分类的记录

                //判断：一级分类id 和 二级分类parentid比较
                if(oneSubject.getId().equals(twoSubject.getParentId())) {
                    TwoSubjectVo twoSubjectVo = new TwoSubjectVo();
                    BeanUtils.copyProperties(twoSubject,twoSubjectVo);
                    twoVoList.add(twoSubjectVo);
                }
            }
            //二级分类list集合放到一级分类的children里面
            oneSubjectVo.setChildren(twoVoList);
        }
        return finalSucjectList;
    }
}
