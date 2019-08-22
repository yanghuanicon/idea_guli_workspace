package com.guli.edusta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.result.Result;
import com.guli.edusta.client.UcenterClient;
import com.guli.edusta.entity.StatisticsDaily;
import com.guli.edusta.mapper.StatisticsDailyMapper;
import com.guli.edusta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-08-08
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    UcenterClient ucenterClient;
    //获取用户模块每天注册人数，添加统计分析表里面
    @Override
    public void getCountRegister(String day) {
        //返回Result对象
        Result result = ucenterClient.registerCount(day);
        //获取每一天注册的人数
        Integer num =(Integer) result.getData().get("count");
        //把获取的数据放到统计分析表中
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setRegisterNum(num);
        //TODO 在实际操作中查询其他模块获取数据，模拟随机数字
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        //添加数据之前先将表中相同日期的数据删除在进行
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper <>();
        queryWrapper.eq("date_calculated",day);
        baseMapper.delete(queryWrapper);
        //添加数据
        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> getDataCount(String begin, String end, String type) {
        //根据日期范围查询统计数量和具体的日期
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper <>();
        wrapper.between("date_calculated",begin,end)
                .select("date_calculated",type)
                .orderByAsc("date_calculated");
        List <StatisticsDaily> dailyList = baseMapper.selectList(wrapper);
        //创建两个集合来保存x和y轴的数据
        List<String> timeList = new ArrayList <>();
        List<Integer> numList = new ArrayList <>();

        //遍历数据集合获取每一个数据
        for (StatisticsDaily daily :dailyList) {
            //获取具体的时间
            String dateCalculated = daily.getDateCalculated();
            timeList.add(dateCalculated);
            //因为用不同的数据所以要进行判断
            switch (type){
                case "register_num" :
                    Integer registerNum = daily.getRegisterNum();
                    numList.add(registerNum);
                    break;
                case "login_num" :
                    Integer loginNum = daily.getLoginNum();
                    numList.add(loginNum);
                    break;
                case "video_view_num" :
                    Integer videoViewNum = daily.getVideoViewNum();
                    numList.add(videoViewNum);
                    break;
                case "course_num" :
                    Integer courseNum = daily.getCourseNum();
                    numList.add(courseNum);
                    break;
            }
        }
        //创建用于封装最后数据的集合
        Map<String,Object> map = new HashMap <>();
        //将两个集合存放到map集合中
        map.put("timeList",timeList);
        map.put("numList",numList);
        return map;
    }
}
