package com.atguigu.msmservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("edumsm/msm")
//@CrossOrigin
public class MsmController {


    @Autowired
    private MsmService msmService;

//    redis自动封装的一个对象
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

//根据手机号发送短信
    @GetMapping("send/{phoneNum}")
    public R sendMsm(@PathVariable String phoneNum){
        //redis中有的话直接返回
        String code = redisTemplate.opsForValue().get(phoneNum);
        if(!StringUtils.isEmpty(code)) return R.ok();

        code = RandomUtil.getFourBitRandom();

        Map<String,Object> map = new HashMap<>();
        map.put("code",code);

        boolean success = msmService.send(map,phoneNum);
        if(success){
//            放到redis中 有效时间设为5分钟
            redisTemplate.opsForValue().set(phoneNum, code,5, TimeUnit.MINUTES);
            return R.ok();
        }

        else return R.error().message("短信发送失败");

    }
}
