package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.teacher.client.VodClient;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.mapper.EduVideoMapper;
import com.guli.teacher.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-08-04
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient vodClient;
    //根据id删除小节
    // TODO 完善删除小节，同时删除小节里面视频
    @Override
    public void removeVideoId(String id) {
        //查询云端视频id
        EduVideo eduVideo = baseMapper.selectById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断视频ID是否为空，不为空的话删除
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeVideo(videoSourceId);
        }
        //删除小节
        baseMapper.deleteById(id);
    }

    //根据课程id删除小节
    // TODO 完善删除小节时候，删除小节里面视频
    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper();
        wrapper.eq("course_id",courseId);
        wrapper.select("video_source_id");
        //获取视频id的集合
        List<String> videoIdList = new ArrayList<>();
        List <EduVideo> eduVideos = baseMapper.selectList(wrapper);
        for (EduVideo eduVide : eduVideos) {
            String videoSourceId = eduVide.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                videoIdList.add(videoSourceId);
            }
        }
        //调用远程接口删除视频
        if(videoIdList.size()>0){
            vodClient.removeVideoList(videoIdList);
        }

        //删除video表中的数据
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper <>();
        wrapper2.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
