package com.titos.rpc.config;

import com.titos.rpc.clients.NormalServiceClient;
import com.titos.rpc.fallback.*;
import feign.Logger;
import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfiguration {
    @Value("${service.feign.connectTimeout:60000}")
    private int connectTimeout;

    @Value("${service.feign.readTimeout:60000}")
    private int readTimeout;
    @Bean
    public Logger.Level logLevel() {
        return Logger.Level.BASIC;
    }
    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeout, readTimeout);
    }

    @Bean
    public BlogServiceClientFallbackFactory blogServiceClientFallbackFactory() {
        return new BlogServiceClientFallbackFactory();
    }
    @Bean
    public NormalServiceClientFallbackFactory normalServiceClientFallbackFactory() {
        return new NormalServiceClientFallbackFactory();
    }
    @Bean
    public SharePlatformServiceClientFallbackFactory sharePlatformServiceClientFallbackFactory() {
        return new SharePlatformServiceClientFallbackFactory();
    }
    @Bean
    public UserServiceClientFallbackFactory userServiceClientFallbackFactory() {
        return new UserServiceClientFallbackFactory();
    }
}
