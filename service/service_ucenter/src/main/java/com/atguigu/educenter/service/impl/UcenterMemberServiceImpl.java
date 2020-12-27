package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterUserVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-20
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    //    登陆,返回生成的token值
    @Override
    public String login(UcenterMember ucenterMember) {

        String phoneNumber = ucenterMember.getMobile();
        String pwd = ucenterMember.getPassword();

        if(StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(pwd)){
            System.out.println("手机号或密码为空");
            return null;
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",phoneNumber);
        wrapper.eq("password",MD5.encrypt(pwd));//一般密码都加密存储
        wrapper.eq("is_disabled",0);

        UcenterMember member = baseMapper.selectOne(wrapper);
        if(member==null){
            System.out.println("用户查询失败");
            return null;
        }

        String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());

        return token;
    }

    @Override
    public void register(RegisterUserVo registerUserVo) {
        String phoneNumber = registerUserVo.getPhoneNumber();
        String password = registerUserVo.getPassword();
        String code = registerUserVo.getCode();//验证码
        String nickname = registerUserVo.getNickname();

        if(StringUtils.isEmpty(phoneNumber) ||StringUtils.isEmpty(password) ||StringUtils.isEmpty(code) ||StringUtils.isEmpty(nickname)){
            System.out.println("有信息项为空");
            return;
        }

        //校验校验验证码
//从redis获取发送的验证码
        String checkCode = redisTemplate.opsForValue().get(phoneNumber);
        if(StringUtils.isEmpty(checkCode) || !checkCode.equals(code)){
            System.out.println("验证码错误或失效");
            return;
        }

//        查询数据库中是否存在相同的手机号码
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",phoneNumber);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            System.out.println("手机号已经被注册");
            return;
        }

        UcenterMember member = new UcenterMember();
        member.setMobile(phoneNumber);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://edu-guli123.oss-cn-beijing.aliyuncs.com/2020/10/07/53ae6c8ed29a46e58660793359d7a1c6file.png");
        baseMapper.insert(member);

    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);

        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
