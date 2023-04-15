package com.titos.admin;

import com.titos.rpc.clients.BlogServiceClient;
import com.titos.rpc.clients.NormalServiceClient;
import com.titos.rpc.clients.SharePlatformServiceClient;
import com.titos.rpc.clients.UserServiceClient;
import com.titos.rpc.config.DefaultFeignConfiguration;
import com.titos.tool.annotions.EnableYunKeAutoUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = {BlogServiceClient.class, SharePlatformServiceClient.class, UserServiceClient.class, NormalServiceClient.class}, defaultConfiguration = DefaultFeignConfiguration.class)
@SpringBootApplication
@EnableDiscoveryClient
@EnableYunKeAutoUtils
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
