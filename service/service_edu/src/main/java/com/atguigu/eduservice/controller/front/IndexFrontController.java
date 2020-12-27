package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //  取八条热门课程和四条热门讲师
    @GetMapping("index")
    public R index(){

        List<EduTeacher> eduTeachers = teacherService.getsomeTeacher(4);
        List<EduCourse> eduCourses = courseService.getsomeCourse(8);

        return R.ok().data("teacherList",eduTeachers).data("courseList",eduCourses);
    }
}
