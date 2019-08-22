package com.guli.eduvod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.guli.eduvod.service.VodService;
import com.guli.eduvod.util.AliyunVodSDKUtils;
import com.guli.eduvod.util.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    //上传视频到阿里云操作
    @Override
    public String uploadAliyunVod(MultipartFile file) {
        try{
            //获取上传名称
            String filename = file.getOriginalFilename();
            //经常传递文件名称不带后缀名名字
            String title = filename.substring(0, filename.lastIndexOf("."));
            //文件输入流
            InputStream in = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET,title,filename,in);
            //创建UploadViodeImpl对象
            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(request);
            //通过response对象获取返回的对象id
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public void removeVideo(String videoId) throws Exception{

            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();

            request.setVideoIds(videoId);

            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");

    }
    //批量删除视频
    @Override
    public void removeVideoList(List<String> videoIdList) throws Exception{
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        //创建request对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        //将id的list集合截取成字符串
        String str = StringUtils.join(videoIdList, ",");
        request.setVideoIds(str);
        //获取响应
        DeleteVideoResponse response = client.getAcsResponse(request);
        System.out.println("RequestId: "+response.getRequestId());
    }
}
