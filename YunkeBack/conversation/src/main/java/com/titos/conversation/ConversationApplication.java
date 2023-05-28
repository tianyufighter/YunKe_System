package com.titos.conversation;

import com.titos.tool.annotions.EnableYunKeAutoUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableYunKeAutoUtils
@SpringBootApplication
@EnableDiscoveryClient
@ServletComponentScan("com.titos.conversation.filter")
public class ConversationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConversationApplication.class, args);
	}

}
