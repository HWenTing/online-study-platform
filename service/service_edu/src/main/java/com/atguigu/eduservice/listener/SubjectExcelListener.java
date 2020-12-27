package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

//    因为SubjectExcelListener不能交给spring管理，所以像要使用EduSubjectService，需要传进来

    private EduSubjectService subjectService;
    public SubjectExcelListener() {}
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取excel内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null) return;

        EduSubject eduOneSubject = this.existOneSubject(subjectService,subjectData.getOneSubjectName());
        if(eduOneSubject==null){//添加一级分类
            eduOneSubject = new EduSubject();
            eduOneSubject.setParentId("0");
            eduOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(eduOneSubject);
        }

        String pid = eduOneSubject.getId();//获取父id
        EduSubject eduTwoSubject = this.existTwoSubject(subjectService,subjectData.getTwoSubjectName(),pid);
        if(eduTwoSubject==null){//添加二级分类
            eduTwoSubject = new EduSubject();
            eduTwoSubject.setParentId(pid);
            eduTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(eduTwoSubject);
        }
    }

//    判断一级分类存不存在
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    //    判断二级分类存不存在
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
