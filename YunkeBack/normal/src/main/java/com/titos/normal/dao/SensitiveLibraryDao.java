package com.titos.normal.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 自定义敏感词所对应的dao层
 * @author Titos
 */
@Mapper
public interface SensitiveLibraryDao {
    /**
     * 查询数据库中所有的敏感词
     * @return 敏感词信息
     */
    List<String> selectAllSensitiveWord();
}
