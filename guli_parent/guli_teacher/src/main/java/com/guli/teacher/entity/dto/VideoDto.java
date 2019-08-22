package com.guli.teacher.entity.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//小节
@Data
public class VideoDto {
    private String id;
    private String title;
    @ApiModelProperty(value = "云服务器上存储的视频文件名称")
    private String videoOriginalName;
    private String videoSourceId; //视频id
}
