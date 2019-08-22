package com.guli.edusta.service;

import com.guli.edusta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-08-08
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void getCountRegister(String day);

    Map<String,Object> getDataCount(String begin, String end, String type);
}
