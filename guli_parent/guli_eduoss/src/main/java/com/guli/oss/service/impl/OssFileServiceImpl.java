package com.guli.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.guli.oss.service.OssFileService;
import com.guli.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;
import java.util.UUID;

@Service
public class OssFileServiceImpl implements OssFileService{

    @Override
    public String uploadAliyun(MultipartFile file) {

        try {
            //获取上传组组件的固定值
            String endPoint = ConstantPropertiesUtil.END_POINT;
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
            //2.创建ossclient对象
            OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
            //3.获取文件信息用于上传
            //文件输入流
            InputStream in = file.getInputStream();
            //文件名称
            String filename = file.getOriginalFilename();

            //添加UUID
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid + filename;

            //实现出 /2019/07/30/
            String dateUrl = new DateTime().toString("yyyy/MM/dd");
            filename = dateUrl + '/' + filename;

            //4.调用ossClient方法实现上传
            ossClient.putObject(bucketName, filename, in);
            //5.关闭ossClient
            ossClient.shutdown();


            //返回上传之后地址，拼接地址
            //https://edu-demo0311.oss-cn-beijing.aliyuncs.com/2019/07/30/bc22737276904a74bb5242f00bc1c01801.jpg
            String uploadUrl = "https://" + bucketName + "." + endPoint + "/" + filename;
            return uploadUrl;

        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }


    }
}
