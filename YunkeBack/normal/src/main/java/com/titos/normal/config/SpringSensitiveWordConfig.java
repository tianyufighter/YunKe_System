package com.titos.normal.config;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.support.allow.WordAllows;
import com.github.houbb.sensitive.word.support.deny.WordDenys;
import com.titos.normal.service.sensitive.impl.MyWordAllow;
import com.titos.normal.service.sensitive.impl.MyWordDeny;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class SpringSensitiveWordConfig {
    @Resource
    private MyWordAllow myWordAllow;
    @Resource
    private MyWordDeny myWordDeny;

    /**
     * 初始化引导类
     * @return 初始化引导类
     */
    @Bean
    public SensitiveWordBs sensitiveWordBs() {
        // 可根据数据库数据判断 动态增加配置
        SensitiveWordBs init =  SensitiveWordBs.newInstance()
                .wordAllow(WordAllows.chains(WordAllows.system(), myWordAllow))
                .wordDeny(WordDenys.chains(WordDenys.system(), myWordDeny))
                .ignoreCase(true) // 忽略大小写
                .ignoreWidth(true) // 忽略半角圆角
                .ignoreNumStyle(true) // 忽略数字的写法
                .ignoreChineseStyle(true) // 忽略中文的书写格式
                .ignoreEnglishStyle(true) // 忽略英文的书写格式
                .ignoreRepeat(true) // 忽略重复词
                .enableNumCheck(true) // 是否启用数字检测。默认连接8位数字是敏感词
                .enableEmailCheck(true) // 是否启用邮箱检测
                .enableUrlCheck(true) // 是否启用链接检测
                .init();
        return init;
    }
}
