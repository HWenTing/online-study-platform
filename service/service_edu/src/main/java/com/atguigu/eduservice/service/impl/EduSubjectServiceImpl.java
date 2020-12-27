package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.vo.SubjectVo;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-07
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {

        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<SubjectVo> getAllSubject() {

        //一级目录
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<EduSubject> eduSubjectsOne = baseMapper.selectList(wrapper);

        //二级目录
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id","0");
        List<EduSubject> eduSubjectstwo = baseMapper.selectList(wrapper2);


        List<SubjectVo> ret = new ArrayList<>();
        HashMap<String,SubjectVo> map = new HashMap<>();
        for(EduSubject e:eduSubjectsOne){
            SubjectVo cur = new SubjectVo();
            cur.setId(e.getId());
            cur.setLabel(e.getTitle());
            map.put(e.getId(),cur);
            ret.add(cur);
        }

        for(EduSubject e:eduSubjectstwo){
            SubjectVo cur = new SubjectVo();
            cur.setId(e.getId());
            cur.setLabel(e.getTitle());

            SubjectVo parent = map.get(e.getParentId());
            if(parent!=null){
                parent.getChildren().add(cur);
            }
        }

        return ret;
    }
}
