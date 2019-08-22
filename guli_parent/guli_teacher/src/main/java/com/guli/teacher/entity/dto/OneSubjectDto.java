package com.guli.teacher.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//一级列表
@Data
public class OneSubjectDto {
    private String id;

    private String title;

    private List<TwoSubjectDto> children = new ArrayList <>();
}
