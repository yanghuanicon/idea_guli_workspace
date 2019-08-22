package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.query.CourseInfoForm;
import com.guli.teacher.entity.query.PublishCourseInfo;
import com.guli.teacher.service.EduCourseService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-08-01
 */
@Api(description = "课程管理模块")
@RestController
@RequestMapping("/teacher/edu-course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;

    //根据id删除课程
    @DeleteMapping("{courseId}")
    public Result deleteCourse(@PathVariable String courseId){
        eduCourseService.removeCourseId(courseId);
        return Result.ok();
    }

    //TODO 完善条件查询带分页
    @GetMapping("getAllCourse")
    public Result getAllCourse() {
        List<EduCourse> list = eduCourseService.list(null);
        return Result.ok().data("items",list);
    }
    //课程发布
    @PutMapping("publishCourse/{courseId}")
    public Result publishCourse(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return Result.ok();
    }

    //做课程确认页面根据id查询数据
    @GetMapping("getPublishCourseInfo/{id}")
    public Result getPublishCourseInfo(@PathVariable String id) {
       PublishCourseInfo courseInfoPublish =eduCourseService.getCourseInfoPublish(id);
       return Result.ok().data("courseInfoPublish",courseInfoPublish);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        eduCourseService.updateCourse(courseInfoForm);
        return Result.ok();
    }

    //根据id查询课程信息
    @ApiOperation(value = "根据id查询课程信息")
    @GetMapping("getCourseInfo/{id}")
    public Result getInfoCourse(@PathVariable String id){
       CourseInfoForm courseInfoForm = eduCourseService.getCourseInfoId(id);
       return Result.ok().data("courseInfo",courseInfoForm);
    }

    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("addCourse")
    public Result addCourse (@RequestBody CourseInfoForm courseInfoForm){
        String courseId = eduCourseService.saveCourse(courseInfoForm);
        return  Result.ok().data("courseId",courseId);
    }

}

