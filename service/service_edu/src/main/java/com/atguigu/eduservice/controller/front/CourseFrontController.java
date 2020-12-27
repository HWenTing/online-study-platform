package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.entity.vo.front.CourseQueryVo;
import com.atguigu.eduservice.entity.vo.front.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin
@RequestMapping("/eduservice/courseFront")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @PostMapping("{page}/{limit}")
    public R getCoursePageList(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) CourseQueryVo courseQueryVo){
        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam, courseQueryVo);
        return  R.ok().data(map);
    }

    @GetMapping("getFrontCourseInfo/{id}")
    public R getFrontCourseInfo(@PathVariable String id){
//        查询课程信息
        CourseWebVo courseWebVo = courseService.getFrontCourseInfoById(id);
//        查询章节信息
        List<ChapterVo> chapterVoList = chapterService.getAllChapterByCourseId(id);

        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList);
    }
}
