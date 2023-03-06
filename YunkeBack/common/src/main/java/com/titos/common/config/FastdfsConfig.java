package com.titos.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class FastdfsConfig {
    @Value("${file.fastdfs.groupName}")
    public static String groupName;
}
