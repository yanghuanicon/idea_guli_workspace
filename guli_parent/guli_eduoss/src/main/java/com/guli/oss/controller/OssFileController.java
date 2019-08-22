package com.guli.oss.controller;


import com.guli.common.result.Result;
import com.guli.oss.service.OssFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss/edu-oss")
@CrossOrigin
public class OssFileController {

    @Autowired
    OssFileService ossFileService;

    @PostMapping("/upload")
    public Result uploadFileAliyunOss(MultipartFile file) {
        //1 获取上传文件  MultipartFile file
        //file参数名称不是随便写的 和文件上传输入项里面name属性值一样 <input type="file" name="file"/>
        String uploadUrl = ossFileService.uploadAliyun(file);
        return Result.ok().data("url",uploadUrl);
    }
}
