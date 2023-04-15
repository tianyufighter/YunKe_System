package com.titos.normal.config;

import com.aliyun.oss.OSSClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
@Data
public class AliyunConfig {
    @Value("${file.aliyun.endpoint}")
    private  String endpoint;
    @Value("${file.aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${file.aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${file.aliyun.bucketName}")
    private  String bucketName;
    @Value("${file.aliyun.fileHost}")
    private  String fileHost;
    /**
     * 阿里云文件存储client
     */
    @Bean
    public OSSClient ossClient() {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }
}
