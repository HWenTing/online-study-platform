package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-08
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String id);

}
