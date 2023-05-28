package com.titos.technicalarchive;

import com.titos.rpc.clients.NormalServiceClient;
import com.titos.rpc.clients.UserServiceClient;
import com.titos.rpc.config.DefaultFeignConfiguration;
import com.titos.tool.annotions.EnableYunKeAutoUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableYunKeAutoUtils
@EnableDiscoveryClient
@EnableFeignClients(clients = {UserServiceClient.class}, defaultConfiguration = DefaultFeignConfiguration.class)
@SpringBootApplication
public class TechnicalArchiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnicalArchiveApplication.class, args);
    }

}
