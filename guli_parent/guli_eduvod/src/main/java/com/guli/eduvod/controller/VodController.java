package com.guli.eduvod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.guli.common.result.Result;
import com.guli.eduvod.service.VodService;
import com.guli.eduvod.util.AliyunVodSDKUtils;
import com.guli.eduvod.util.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/vid/")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    //根据id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth (@PathVariable String id){
        try{
            DefaultAcsClient client =
                    AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
             //创建请求对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse acsResponse = client.getAcsResponse(request);
            String playAuth = acsResponse.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    //根据id批量删除视频
    @DeleteMapping("delete-list")
    public Result removeVideoList(@RequestParam("videoIdList") List<String> videoIdList){
        try {
            vodService.removeVideoList(videoIdList);
            return Result.ok().message("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("删除失败");
        }

    }
    //上传视频到阿里云操作
    @PostMapping("uploadVideoAliyun")
    public Result uploadVideoAliyun(MultipartFile file) {
        String videoId = vodService.uploadAliyunVod(file);
        return Result.ok().data("videoId",videoId);
    }
    //根据id删除视频
    @DeleteMapping("{videoId}")
    public Result removeVideo (@PathVariable String videoId){
        try {
            vodService.removeVideo(videoId);
            return Result.ok().message("视频删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("视频删除失败");
        }


    }
}
