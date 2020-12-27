package com.atguigu.eduservice.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectVo {

    private String id;
    private String label;

    private List<SubjectVo> children = new ArrayList<>();
}
