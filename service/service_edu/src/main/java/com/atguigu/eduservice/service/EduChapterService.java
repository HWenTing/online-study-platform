package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-08
 */
public interface EduChapterService extends IService<EduChapter> {

    //章节列表
    List<ChapterVo> getAllChapterByCourseId(String courseId);

    //删除章节
    boolean deleteChapter(String chapterId);

    void removeByCourseId(String id);
}
