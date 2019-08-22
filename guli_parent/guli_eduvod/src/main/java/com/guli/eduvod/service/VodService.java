package com.guli.eduvod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    String uploadAliyunVod(MultipartFile file);

    void removeVideo(String videoId) throws Exception;

    void removeVideoList(List<String> videoIdList) throws Exception;
}
