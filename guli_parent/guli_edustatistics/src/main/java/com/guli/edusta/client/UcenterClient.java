package com.guli.edusta.client;

import com.guli.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient("edu-educenter")
@Component
public interface UcenterClient {
    @GetMapping("/educenter/ucenter-member/getCount/{day}")
    public Result registerCount(@PathVariable("day") String day);
}
