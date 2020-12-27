package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterUserVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-20
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    //    登陆,返回生成的token值
    String login(UcenterMember ucenterMember);

//    注册
    void register(RegisterUserVo registerUserVo);

//    通过微信openid值得到用户信息
    UcenterMember getByOpenid(String openid);

    Integer countRegisterDay(String day);
}
