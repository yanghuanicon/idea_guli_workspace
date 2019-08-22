package com.guli.edusta.controller;


import com.guli.common.result.Result;
import com.guli.edusta.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-08-08
 */
@RestController
@RequestMapping("/edusta/statistics-daily")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    StatisticsDailyService statisticsDailyService;

    //返回图标数据用于显示
    @GetMapping("getCountData/{begin}/{end}/{type}")
    public Result getCountData(@PathVariable String begin,
                               @PathVariable String end,
                               @PathVariable String type){
       Map<String,Object> map= statisticsDailyService.getDataCount(begin,end,type);
       return Result.ok().data("map",map);
    }

    //获取用户模块每天注册人数，添加到统计分析表中
    @GetMapping("getRegisterCount/{day}")
    public Result getRegisterCount(@PathVariable("day") String day) {
        statisticsDailyService.getCountRegister(day);
        return Result.ok();
    }
}

