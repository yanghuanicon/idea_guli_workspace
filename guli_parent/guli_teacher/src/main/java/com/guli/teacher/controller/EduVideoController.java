package com.guli.teacher.controller;


import com.google.common.annotations.GwtCompatible;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-08-04
 */
@Api(description = "小节视频管理模块")
@RestController
@RequestMapping("/teacher/edu-video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    EduVideoService eduVideoService;

    //添加小节
    @ApiOperation("添加小节")
    @PostMapping("saveVideo")
    public Result saveVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return Result.ok();
    }
    //删除小节
    @ApiOperation("删除小节")
    @DeleteMapping("{id}")
    public Result deleteVideo(@PathVariable String id){
        eduVideoService.removeVideoId(id);
        return Result.ok();
    }
    //根据id查询小节
    @ApiOperation("根据id查看小节")
    @GetMapping("getVideoInfo/{id}")
    public Result getVideoInfo(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        return Result.ok().data("eduVideo",eduVideo);
    }
    //修改小节
    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return Result.ok();
    }
}

