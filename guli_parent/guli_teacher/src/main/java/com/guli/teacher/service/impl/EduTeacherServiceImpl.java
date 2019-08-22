package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.entity.query.TeacherQuery;
import com.guli.teacher.mapper.EduTeacherMapper;
import com.guli.teacher.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-24
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {

        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper <>();

        queryWrapper.orderByAsc("sort");

        if( teacherQuery == null) {
            baseMapper.selectPage(pageParam,null);
            return ;
        }
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("begin",begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("end",end);
        }
        baseMapper.selectPage(pageParam,queryWrapper);
    }
    //分页查询讲师列表
    @Override
    public Map<String, Object> getFrontTeacherPage(Page <EduTeacher> teacherPage) {
        baseMapper.selectPage(teacherPage,null);
        //把pageTeacher里面分页数据封装到map集合
        List<EduTeacher> records = teacherPage.getRecords();//每页数据list集合
        long total = teacherPage.getTotal();//总记录数
        long size = teacherPage.getSize();//每页记录数
        long pages = teacherPage.getPages();//总页数
        long current = teacherPage.getCurrent();//当前页
        boolean hasPrevious = teacherPage.hasPrevious();//是否有上一页
        boolean hasNext = teacherPage.hasNext();//是否有下一页

        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
