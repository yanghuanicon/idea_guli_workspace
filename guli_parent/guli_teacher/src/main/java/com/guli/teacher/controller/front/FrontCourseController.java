package com.guli.teacher.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.dto.ChapterDto;
import com.guli.teacher.entity.query.CourseInfo;
import com.guli.teacher.service.EduChapterService;
import com.guli.teacher.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher/frontCourse")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    EduCourseService courseService;

    @Autowired
    EduChapterService chapterService;

    //根据课程详情
    @GetMapping("getCourseInfo/{id}")
    public Result getCourseInfo(@PathVariable String id) {
        //1 根据课程id查询大纲（章节和小节）
        List<ChapterDto> allChapterVideo = chapterService.getAllChapterVideo(id);

        //2 编写sql语句根据课程id查询课程信息
        CourseInfo courseInfo = courseService.getCourseBaseInfo(id);

        return Result.ok().data("allChapterVideo",allChapterVideo).data("courseInfo",courseInfo);
    }
    @GetMapping("getCourseByPage/{page}/{limit}")
    public Result getCourseByPage(@PathVariable Long page,
                                  @PathVariable Long limit){
        Page<EduCourse> pageCourse = new Page <>(page,limit);
        Map<String,Object> map=courseService.getPageCourse(pageCourse);
        return Result.ok().data(map);
    }
}
