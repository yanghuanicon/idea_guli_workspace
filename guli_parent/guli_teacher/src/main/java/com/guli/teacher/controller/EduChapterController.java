package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduChapter;
import com.guli.teacher.entity.dto.ChapterDto;
import com.guli.teacher.service.EduChapterService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-08-04
 */
@RestController
@RequestMapping("/teacher/edu-chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //查询课程里面的所有章节和小节数据，使用dto进行封装
    @GetMapping("getAllChapterVideoByCourse/{courseId}")
    public Result getAllChapterVideoByCourse(@PathVariable String courseId) {
        List<ChapterDto> list = eduChapterService.getAllChapterVideo(courseId);
        return Result.ok().data("items",list);
    }

    //添加章节
    @PostMapping("addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return Result.ok();
    }
    //根据id查询章节
    @GetMapping("getChapterId/{chapterId}")
    public Result getChapterId(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return Result.ok().data("eduChapter",eduChapter);
    }
    //修改章节
    @PostMapping("updateChapter")
    public Result updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return Result.ok();
    }
    //删除章节
    @DeleteMapping("deleteChapter/{id}")
    public Result deleteChapter(@PathVariable String id) {
        eduChapterService.removeChapterId(id);
        return Result.ok();
    }
}

