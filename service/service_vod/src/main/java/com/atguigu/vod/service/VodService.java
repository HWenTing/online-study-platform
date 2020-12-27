package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    //上传视频到阿里云
    String uploadAlyVideo(MultipartFile file);

    void deleteAlyVideo(String id);

    void deleteMoreAlyVideo(List<String> idList);
}
