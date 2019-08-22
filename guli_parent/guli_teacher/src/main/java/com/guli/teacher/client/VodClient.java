package com.guli.teacher.client;

import com.guli.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("EDU-EDUVOD")
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/vid/{videoId}")
    public Result removeVideo (@PathVariable("videoId") String videoId);

    @DeleteMapping("/eduvod/vid/delete-list")
    public Result removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);
}
