package com.guli.teacher.mapper;

import com.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.teacher.entity.query.CourseInfo;
import com.guli.teacher.entity.query.PublishCourseInfo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2019-08-01
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    PublishCourseInfo getPublishCourseInfo(String courseId);

    //2 编写sql语句根据课程id查询课程信息
    CourseInfo getCourseBaseInfo(String id);
}
