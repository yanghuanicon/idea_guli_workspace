package com.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssFileService {

    public String uploadAliyun(MultipartFile file);
}
