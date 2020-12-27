package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-08
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;
    //章节列表
    @Override
    public List<ChapterVo> getAllChapterByCourseId(String courseId) {

        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);

        List<ChapterVo> ret = new ArrayList<>();

        Map<String,ChapterVo> map = new HashMap<>();
        for(EduChapter eduChapter:eduChapters){
            ChapterVo cur = new ChapterVo();
            cur.setId(eduChapter.getId());
            cur.setTitle(eduChapter.getTitle());
            map.put(cur.getId(),cur);
            ret.add(cur);
        }

        QueryWrapper<EduVideo> wrappervideo = new QueryWrapper<>();
        wrappervideo.eq("course_id",courseId);
        List<EduVideo> eduVideos = eduVideoService.list(wrappervideo);

        for(EduVideo eduVideo:eduVideos){
//            ChapterVo cur = new ChapterVo();
//            cur.setId(eduVideo.getId());
//            cur.setTitle(eduVideo.getTitle());
            if(map.get(eduVideo.getChapterId())!=null)
                map.get(eduVideo.getChapterId()).getChildren().add(eduVideo);
        }

        return ret;
    }

    //删除章节
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int cnt = eduVideoService.count(wrapper);
        if(cnt>0){//存在小节，则不进行删除
            System.out.println("存在小节，不能直接删除");
            return false;
        }else{
            int res = baseMapper.deleteById(chapterId);
            return res>0;
        }
    }

    @Override
    public void removeByCourseId(String id) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        baseMapper.delete(queryWrapper);
    }
}
