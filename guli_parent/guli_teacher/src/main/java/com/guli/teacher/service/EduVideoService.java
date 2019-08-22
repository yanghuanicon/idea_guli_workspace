package com.guli.teacher.service;

import com.guli.teacher.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-08-04
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoId(String id);

    void deleteChapterByCourseId(String courseId);
}
