package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.front.CourseQueryVo;
import com.atguigu.eduservice.entity.vo.front.CourseWebVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-08
 */
public interface EduCourseService extends IService<EduCourse> {
    //添加课程信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //查询课程信息
    CourseInfoVo getCourseInfoByCourseId(String courseId);

    //    修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程确认信息
    CoursePublishVo getPublishCourseInfo(String id);

    //删除课程
    void removeCourse(String id);

    //取八条热门课程
    List<EduCourse> getsomeCourse(int i);

    //根据讲师查询课程列表
    List<EduCourse> getCourseListByTeacher(String id);

//   根据查询条件查询课程信息
    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo);

    CourseWebVo getFrontCourseInfoById(String id);
}
