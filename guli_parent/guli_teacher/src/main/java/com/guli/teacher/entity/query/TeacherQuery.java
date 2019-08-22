package com.guli.teacher.entity.query;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@ApiModel(value = "teacher查询对象",description = "讲师对象查询封装")
@Data
public class TeacherQuery implements Serializable{

    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "教师级别，头衔 1.高级讲师,2.首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间" , example = "2019-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间",example = "2019-01-01 10:10:10")
    private String end;
}
