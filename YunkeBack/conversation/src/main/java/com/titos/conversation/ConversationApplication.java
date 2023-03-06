package com.titos.conversation;

import com.titos.tool.annotions.EnableYunKeAutoUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ddgo
 */
@EnableYunKeAutoUtils
@SpringBootApplication
@MapperScan("com.titos.conversation.dao")
@ServletComponentScan("com.titos.conversation.filter")
@EnableTransactionManagement
@EnableDiscoveryClient
public class ConversationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConversationApplication.class, args);
    }
}
