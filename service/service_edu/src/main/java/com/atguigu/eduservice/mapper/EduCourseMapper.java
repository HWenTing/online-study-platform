package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.front.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2020-10-08
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo selectCoursePublishVoById(String id);

    public CourseWebVo selectInfoWebById(String courseId);

}
