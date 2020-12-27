package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-10-08
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;


    //条件查询带分页的方法
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> pageCourse = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String name = courseQuery.getName();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();

        if(!StringUtils.isEmpty(name))
            wrapper.like("title",name);//这传的是表中的字段名称，不是实体类的属性名称
        if(!StringUtils.isEmpty(status))
            wrapper.eq("status",status);
        if(!StringUtils.isEmpty(begin))
            wrapper.ge("gmt_create",begin);
        if(!StringUtils.isEmpty(end))
            wrapper.le("gmt_create",end);

        wrapper.orderByDesc("gmt_create");
        eduCourseService.page(pageCourse,wrapper);
        long total = pageCourse.getTotal();
        List<EduCourse> list = pageCourse.getRecords();
        return R.ok().data("total",total).data("rows",list);
    }

    //添加课程信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String coursrId = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",coursrId);
    }

    //根据id查询课程信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfoByCourseId(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoByCourseId(courseId);
        return R.ok().data("info",courseInfoVo);
    }

//    修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = eduCourseService.getPublishCourseInfo(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    //发布课程
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程 有小节的 小节也删掉
    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id){
        eduCourseService.removeCourse(id);
        return R.ok();
    }
}

