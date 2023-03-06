package com.titos.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName RpcApplication
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/4 17:22
 **/
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class RpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcApplication.class, args);
    }

}
