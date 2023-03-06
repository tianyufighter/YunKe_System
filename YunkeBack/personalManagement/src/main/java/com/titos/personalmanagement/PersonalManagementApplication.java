package com.titos.personalmanagement;

import com.titos.tool.annotions.EnableYunKeAutoUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Titos
 */
@EnableFeignClients(basePackages = "com.titos.rpc.file") // 开启feign
@EnableDiscoveryClient // 开启nacos服务注册与发现
@EnableYunKeAutoUtils
@SpringBootApplication
public class PersonalManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalManagementApplication.class, args);
    }

}
