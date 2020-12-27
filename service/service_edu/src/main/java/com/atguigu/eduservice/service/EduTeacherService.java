package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-09-27
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //取几个热门讲师
    List<EduTeacher> getsomeTeacher(int i);

//    获取讲师列表
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageParam);
}
