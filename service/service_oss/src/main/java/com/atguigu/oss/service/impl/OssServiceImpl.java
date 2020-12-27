package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantProtertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAdvatar(MultipartFile file) {

        String endpoint = ConstantProtertiesUtils.END_POINT;
        String accessKeyId = ConstantProtertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantProtertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantProtertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String fileName = file.getOriginalFilename();
            //为了防止文件名称冲突，可以添加随机唯一值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;
            //也可以再加上日期进行分类 年/月/日/文件名
            String date = new DateTime().toString("yyyy/MM/dd");
            fileName = date+"/"+fileName;

            //调用oss上传文件
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            String uploadUrl = "http://" + bucketName + "." + endpoint + "/" + fileName;
            return uploadUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
