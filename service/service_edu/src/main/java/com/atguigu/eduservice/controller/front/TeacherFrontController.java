package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin
@RequestMapping("/eduservice/teacherFront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    @GetMapping(value = "{page}/{limit}")
    public R pageList(@PathVariable Long page, @PathVariable Long limit){
        Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(pageParam);
        return R.ok().data(map);
    }

    @GetMapping("getInfoById/{id}")
    public R getInfoById(@PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        List<EduCourse> courseList = courseService.getCourseListByTeacher(id);//根据讲师查询课程列表

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}
