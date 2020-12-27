package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    //删除阿里云上id视频
    @DeleteMapping("/eduvod/video/deleteAlyVideo/{id}")
    public R deleteAlyVideo(@PathVariable("id") String id);

    //删除阿里云上id视频
    @DeleteMapping("/eduvod/video/deleteBatch")
    public R deleteMoreAlyVideo(@RequestParam("idList") List<String> idList);
}
