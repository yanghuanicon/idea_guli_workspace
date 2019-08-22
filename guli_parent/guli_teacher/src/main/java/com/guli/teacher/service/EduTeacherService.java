package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.query.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-24
 */
public interface EduTeacherService extends IService<EduTeacher> {
    /**
     *  根据多个条件查询讲师列表
     * @param pageParam
     * @param teacherQuery
     */
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    Map<String,Object> getFrontTeacherPage(Page<EduTeacher> teacherPage);

}
