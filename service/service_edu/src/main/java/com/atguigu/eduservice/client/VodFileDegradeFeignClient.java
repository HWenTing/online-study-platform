package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {//熔断出错以后会执行对应的方法
    @Override
    public R deleteAlyVideo(String id) {
        return R.error().message("time out");
    }

    @Override
    public R deleteMoreAlyVideo(List<String> idList) {
        return R.error().message("time out");
    }
}
