package com.titos.normal.service.sensitive.impl;

import com.github.houbb.sensitive.word.api.IWordAllow;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 从数据库中获取不被当作敏感词的词
 */
@Component
public class MyWordAllow implements IWordAllow {
    @Override
    public List<String> allow() {
        return new ArrayList<>();
    }
}
