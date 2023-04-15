package com.titos.admin.dao;

import com.titos.admin.model.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 通知公告表 数据层
 * @author Titos
 */
@Mapper
public interface NoticeDao {
    /**
     * 查询公告信息
     * @param noticeId 公告ID
     * @return 公告信息
     */
    Notice selectNoticeById(Integer noticeId);

    /**
     * 查询公告列表
     * @param notice 公告信息
     * @return 公告集合
     */
    List<Notice> selectNoticeList(Notice notice);

    /**
     * 新增公告
     * @param notice 公告信息
     * @return 结果
     */
    Integer insertNotice(Notice notice);

    /**
     * 修改公告
     * @param notice 公告信息
     * @return 结果
     */
    Integer updateNotice(Notice notice);

    /**
     * 根据公告的id删除对应的公告信息
     * @param noticeId 公告ID
     * @return 结果
     */
    Integer deleteNoticeById(Integer noticeId);
}
