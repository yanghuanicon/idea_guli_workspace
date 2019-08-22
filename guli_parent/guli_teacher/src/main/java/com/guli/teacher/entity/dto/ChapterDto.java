package com.guli.teacher.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterDto {
    private String id;
    private String title;

    //表示所有的小节
    private List<VideoDto> children = new ArrayList<>();
}
