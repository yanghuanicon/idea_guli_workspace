package com.guli.teacher.service;

import com.guli.teacher.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.dto.OneSubjectDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-31
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<String> importData(MultipartFile file);

    List<OneSubjectDto> getSubjectAll();

    Boolean removeSubjectById(String id);
}
