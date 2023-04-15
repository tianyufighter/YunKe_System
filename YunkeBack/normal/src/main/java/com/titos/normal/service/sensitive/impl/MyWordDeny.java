package com.titos.normal.service.sensitive.impl;

import com.github.houbb.sensitive.word.api.IWordDeny;
import com.titos.normal.dao.SensitiveLibraryDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 从数据库中获取自定义的敏感词
 */
@Component
public class MyWordDeny implements IWordDeny {
    @Resource
    private SensitiveLibraryDao sensitiveLibraryDao;
    @Override
    public List<String> deny() {
        return sensitiveLibraryDao.selectAllSensitiveWord();
    }
}
