package com.atguigu.educenter.entity.vo;


import lombok.Data;

@Data
public class RegisterUserVo {

    private String phoneNumber;

    private String password;

    private String code;//验证码

    private String nickname;
}
