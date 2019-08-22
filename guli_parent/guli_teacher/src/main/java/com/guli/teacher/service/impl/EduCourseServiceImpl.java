package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduCourseDescription;
import com.guli.teacher.entity.query.CourseInfo;
import com.guli.teacher.entity.query.CourseInfoForm;
import com.guli.teacher.entity.query.PublishCourseInfo;
import com.guli.teacher.handler.EduException;
import com.guli.teacher.mapper.EduCourseMapper;
import com.guli.teacher.service.EduChapterService;
import com.guli.teacher.service.EduCourseDescriptionService;
import com.guli.teacher.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.teacher.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-08-01
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //注入小节
    @Autowired
    EduVideoService eduVideoService;

    //注入章节
    @Autowired
    EduChapterService eduChapterService;

    //注入描述
    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;
    //添加课程
    @Override
    public String saveCourse(CourseInfoForm courseInfoForm) {
        //添加课程的基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        String courseId = eduCourse.getId();
        //判断是否添加成功
        if(insert <= 0) {
            throw new EduException(20001,"添加课程信息失败");
        }

        //添加课程详情
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        String id = eduCourse.getId();
        String description = courseInfoForm.getDescription();
        if(!description.isEmpty()){
            eduCourseDescription.setId(id);
            eduCourseDescription.setDescription(description);
            eduCourseDescriptionService.save(eduCourseDescription);

        }
        return courseId;

    }
    //根据id查询课程
    @Override
    public CourseInfoForm getCourseInfoId(String id) {
        //根据id查询课程表
        EduCourse eduCourse = baseMapper.selectById(id);
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);
        //根据id查询描述表
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        String description = eduCourseDescription.getDescription();
        if(!StringUtils.isEmpty(description)){
            courseInfoForm.setDescription(description);
        }
        return courseInfoForm;
    }
    //修改课程信息
    @Override
    public void updateCourse(CourseInfoForm courseInfoForm) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if (result <= 0){
            throw new EduException(20001,"修改失败");
        }
        //修改课程描述
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescription.setId(courseInfoForm.getId());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }
    //根据id查询数据用于课程确认页面
    @Override
    public PublishCourseInfo getCourseInfoPublish(String courseId) {
        PublishCourseInfo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }
    //根据课程id删除课程
    @Override
    public void removeCourseId(String courseId) {
        //根据课程id删除小节
        eduVideoService.deleteChapterByCourseId(courseId);

        //根据课程id删除章节
        eduChapterService.deleteChapterByCourseId(courseId);

        //删除课程描述
        eduCourseDescriptionService.removeById(courseId);

        //删除课程
        int result = baseMapper.deleteById(courseId);
        if(result<=0){
            throw new EduException(20001,"删除失败");
        }
    }
    //根据讲师id查询课程里婊
    @Override
    public List<EduCourse> getCourseListByTeacherId(String id) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper <>();
        wrapper.eq("teacher_id",id);
        List <EduCourse> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public Map<String, Object> getPageCourse(Page<EduCourse> pageCourse) {
        baseMapper.selectPage(pageCourse,null);
        //把pageTeacher里面分页数据封装到map集合
        List<EduCourse> records = pageCourse.getRecords();//每页数据list集合
        long total = pageCourse.getTotal();//总记录数
        long size = pageCourse.getSize();//每页记录数
        long pages = pageCourse.getPages();//总页数
        long current = pageCourse.getCurrent();//当前页
        boolean hasPrevious = pageCourse.hasPrevious();//是否有上一页
        boolean hasNext = pageCourse.hasNext();//是否有下一页

        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseInfo getCourseBaseInfo(String id) {
        this.updatePageViewCount(id);
        return baseMapper.getCourseBaseInfo(id);
    }

    @Override
    public void updatePageViewCount(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        eduCourse.setViewCount(eduCourse.getViewCount()+1);
        baseMapper.updateById(eduCourse);
    }
}
