package com.titos.shareplatform;

import com.titos.rpc.clients.NormalServiceClient;
import com.titos.rpc.clients.UserServiceClient;
import com.titos.rpc.config.DefaultFeignConfiguration;
import com.titos.tool.annotions.EnableYunKeAutoUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableFeignClients(clients = {UserServiceClient.class, NormalServiceClient.class}, defaultConfiguration = DefaultFeignConfiguration.class)
@EnableYunKeAutoUtils
@SpringBootApplication
@EnableDiscoveryClient
public class SharePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharePlatformApplication.class, args);
    }

}
