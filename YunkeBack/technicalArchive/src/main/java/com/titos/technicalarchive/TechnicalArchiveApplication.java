package com.titos.technicalarchive;

import com.titos.tool.annotions.EnableYunKeAutoUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/11 20:45
 * @Version: 1.0.0
 * @Description:
 */
@EnableYunKeAutoUtils
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.titos.technicalarchive.dao")
public class TechnicalArchiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(TechnicalArchiveApplication.class, args);
    }
}
