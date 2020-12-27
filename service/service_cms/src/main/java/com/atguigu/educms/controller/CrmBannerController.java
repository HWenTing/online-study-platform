package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-10-17
 */
@RestController
@RequestMapping("/educms/banner")
//@CrossOrigin
public class CrmBannerController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("getAllBanners")
    public R getAllBanners(){
        List<CrmBanner> crmBanners = bannerService.getsomeBanner(2);
        return R.ok().data("list",crmBanners);
    }
}

