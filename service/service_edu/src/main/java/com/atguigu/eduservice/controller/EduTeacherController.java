package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-09-27
 */
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

//    查询所有讲师
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> ret = teacherService.list(null);
        return R.ok().data("items",ret);
    }

    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id){
        boolean ret =  teacherService.removeById(id);
        if(ret) return R.ok();
        else return R.error();
    }

    //分页查询讲师 current当前页 limit每页记录数
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //调用方法时，回将结果封装在pageTeacher中
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> list = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",list);
    }

    //条件查询带分页的方法
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();//这里要用Integer，不能用int，因为可能为空，Integer能接到，int会报错
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name))
            wrapper.like("name",name);//这传的是表中的字段名称，不是实体类的属性名称
        if(!StringUtils.isEmpty(level))
            wrapper.eq("level",level);
        if(!StringUtils.isEmpty(begin))
            wrapper.ge("gmt_create",begin);
        if(!StringUtils.isEmpty(end))
            wrapper.le("gmt_create",end);

        wrapper.orderByDesc("gmt_create");
        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> list = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",list);
    }

    //添加讲师
    @PostMapping("addTeacher")
    public R addTeaher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save) return R.ok();
        else return R.error();
    }

    //根据id查询讲师
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //修改讲师
    @PostMapping("updataTeacher")
    public R updataTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag) return R.ok();
        else return R.error();
    }

}

