package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    //    发送短信
    @Override
    public boolean send(Map<String, Object> map, String phoneNum) {
        if(StringUtils.isEmpty(phoneNum)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "aliyunossfilekeyid",
                        "aliyunossfilekeysecret");

        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
//request.setProtocol(ProtocolType.HTTPS);
//        固定配置
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
//        发送相关
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", "纸月廿的宠物之家");//阿里云短信签名名称
        request.putQueryParameter("TemplateCode", "SMS_204945337");//阿里云短息模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));//要传递json的格式

        try {
            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
