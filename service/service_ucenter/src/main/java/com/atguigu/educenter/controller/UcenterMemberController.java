package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterUserVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-10-20
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

//    登陆
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember ucenterMember){
//        生成token值 通过jwt得到
        String token = ucenterMemberService.login(ucenterMember);
        return R.ok().data("token",token);
    }

//    注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterUserVo registerUserVo){
        ucenterMemberService.register(registerUserVo);
        return R.ok();
    }

//    "根据token获取登录信息"
    @GetMapping("getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(id);
        return R.ok().data("member",member);
    }

//    查询某一天的注册人数
    @GetMapping(value = "countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer cnt = ucenterMemberService.countRegisterDay(day);
        return R.ok().data("countRegister", cnt);
    }
}

