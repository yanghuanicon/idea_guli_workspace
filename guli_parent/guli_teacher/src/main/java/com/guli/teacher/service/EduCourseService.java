package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.query.CourseInfo;
import com.guli.teacher.entity.query.CourseInfoForm;
import com.guli.teacher.entity.query.PublishCourseInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-08-01
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourse(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoId(String id);

    void updateCourse(CourseInfoForm courseInfoForm);

    PublishCourseInfo getCourseInfoPublish(String id);

    void removeCourseId(String courseId);

    List<EduCourse> getCourseListByTeacherId(String id);

    Map<String,Object> getPageCourse(Page<EduCourse> pageCourse);

    CourseInfo getCourseBaseInfo(String id);

    void updatePageViewCount(String id);
}
