package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.entity.query.TeacherQuery;
import com.guli.teacher.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-24
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/teacher/edu-teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //login
    //{"code":20000,"data":{"token":"admin"}}
    @GetMapping("login")
    public Result login() {
        return Result.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
    public Result info() {
        return Result.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
    @ApiOperation(value = "所有讲师列表")
    @GetMapping
    public Result getTeacherList(){
       /* try {
            int c = 10/0;
        }catch (Exception e){
            e.printStackTrace();
            throw new EduException(3000,"自定义异常管用啦？？？？");
        }*/

        List <EduTeacher> list = eduTeacherService.list(null);
        return Result.ok().data("list",list);
    }
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public Result removeById(
            @ApiParam(name = "id",value = "讲师ID",required = true)
            @PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        if(b){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @ApiOperation(value = "分页讲师列表")
    @PostMapping("{page}/{limit}")
    public Result pageList(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherQuery", value = "查询对象",required = true)
            @RequestBody(required = false) TeacherQuery teacherQuery){

        Page<EduTeacher> pageParam = new Page <>(page,limit);
        eduTeacherService.pageQuery(pageParam,teacherQuery);
        List <EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return Result.ok().data("total",total).data("rows",records);
    }
    @ApiOperation(value = "添加讲师")
    @PostMapping
    public Result save(
            @ApiParam(name = "teacher",value = "讲师对象",required = true)
            @RequestBody EduTeacher teacher){
        eduTeacherService.save(teacher);
        return Result.ok();
    }

    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("{id}")
    public Result getById(
            @ApiParam(name = "id",value = "删除的讲师id")
            @PathVariable String id){

        EduTeacher teacher = eduTeacherService.getById(id);
        return Result.ok().data("teacher",teacher);
    }

    @ApiOperation(value = "根据id修改讲师")
    //修改讲师
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean result = eduTeacherService.updateById(eduTeacher);
        if(result) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

}

