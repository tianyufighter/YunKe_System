package com.titos.shareplatform.dao;

import com.titos.shareplatform.model.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface NoticeDao {
    /**
     * 按照时间降序查询所有的通告
     * @return
     */
    List<Notice> selectAllNoticeDesc();
}
