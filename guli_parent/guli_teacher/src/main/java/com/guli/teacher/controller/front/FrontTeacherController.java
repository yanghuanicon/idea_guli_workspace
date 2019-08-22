package com.guli.teacher.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.service.EduCourseService;
import com.guli.teacher.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher/frontTeacher")
@CrossOrigin
public class FrontTeacherController {

    @Autowired
    EduTeacherService eduTeacherService;

    @Autowired
    EduCourseService eduCourseService;

    //根据讲师id查询讲师详情
    @GetMapping("getTeacherInfo/{id}")
    public Result getTeacherInfo(@PathVariable String id){
        //根据id查询讲师的基本信息
        EduTeacher teacher = eduTeacherService.getById(id);
        //根据讲师id查询课程
        List<EduCourse> list= eduCourseService.getCourseListByTeacherId(id);
        return Result.ok().data("teacher",teacher).data("list",list);
    }

    //分页查询讲师列表
    @GetMapping("getFrontTeacherPage/{page}/{limit}")
    public Result getFrontTeacherPage(@PathVariable Long page,
                                      @PathVariable Long limit){
        Page<EduTeacher> teacherPage = new Page <>(page,limit);
        Map<String,Object> map = eduTeacherService.getFrontTeacherPage(teacherPage);
        return Result.ok().data(map);
    }
}
