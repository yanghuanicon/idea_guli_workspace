package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.dto.OneSubjectDto;
import com.guli.teacher.service.EduSubjectService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-31
 */
@Api(description = "课程科目")
@RestController
@RequestMapping("/teacher/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //导入课程分类
    @ApiOperation("导入课程分类")
    @PostMapping("import")
    public Result importSubjectData(MultipartFile file){
        //获取上传文件
        List<String> msg=eduSubjectService.importData(file);
        //判断如果返回msg没有数据
        if (msg.size() >0) {//有提示显示错误信息
            return Result.error().message("部分数据导入成功").data("msg",msg);

        }else{
            return Result.ok();
        }
        }
        //返回显示的json数据
        @ApiOperation("回显数据")
        @GetMapping("getSubjectAll")
        public Result getSubjectAll(){
            List<OneSubjectDto> list=eduSubjectService.getSubjectAll();
            return Result.ok().data("items",list);
        }
        //根据id删除数据
        @ApiOperation("根据id删除数据")
        @DeleteMapping("{id}")
        public Result deleteSubjectId(@PathVariable String id){
            Boolean flag = eduSubjectService.removeSubjectById(id);
            if(flag){
                return Result.ok();
            }else {
                return Result.error();
            }
        }
        //添加一级分类
        @ApiOperation("添加一级分类")
        @PostMapping("addOneSubject")
        public Result addSubjectOne(@RequestBody EduSubject eduSubject){
            eduSubject.setParentId("0");
            boolean save = eduSubjectService.save(eduSubject);
            if(save) {
                return Result.ok();
            }else {
                return Result.error();
            }
        }
        @ApiOperation("添加二级分类")
        @PostMapping("addTwoSubject")
        public Result addSubjectTwo (@RequestBody EduSubject eduSubject){
            boolean save = eduSubjectService.save(eduSubject);
            if(save) {
                return Result.ok();
            } else {
                return Result.error();
            }
        }
}

