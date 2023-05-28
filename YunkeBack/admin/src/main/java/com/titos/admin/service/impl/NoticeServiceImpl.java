package com.titos.admin.service.impl;

import com.titos.admin.dao.NoticeDao;
import com.titos.admin.model.Notice;
import com.titos.admin.service.NoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeDao noticeDao;

    /**
     * 查询公告信息
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public Notice selectNoticeById(Integer noticeId) {
        return noticeDao.selectNoticeById(noticeId);
    }

    /**
     * 查询公告列表
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<Notice> selectNoticeList(Notice notice) {
        return noticeDao.selectNoticeList(notice);
    }

    /**
     * 新增公告
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public Integer insertNotice(Notice notice) {
        return noticeDao.insertNotice(notice);
    }

    /**
     * 修改公告
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public Integer updateNotice(Notice notice) {
        return noticeDao.updateNotice(notice);
    }

    /**
     * 删除公告对象
     * @param noticeId 公告ID
     * @return 结果
     */
    @Override
    public Integer deleteNoticeById(Integer noticeId) {
        return noticeDao.deleteNoticeById(noticeId);
    }
}
