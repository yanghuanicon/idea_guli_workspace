package com.guli.teacher.service;

import com.guli.teacher.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.dto.ChapterDto;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-08-04
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterDto> getAllChapterVideo(String courseId);

    void removeChapterId(String id);

    void deleteChapterByCourseId(String courseId);
}
