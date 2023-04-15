package com.titos.shareplatform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.shareplatform.dao.NoticeDao;
import com.titos.shareplatform.model.Notice;
import com.titos.shareplatform.service.NoticeService;
import com.titos.shareplatform.vo.NoticeVO;
import com.titos.tool.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeDao noticeDao;
    @Override
    public CommonResult<PageInfo<NoticeVO>> listNotice(Integer pageNum, Integer pageSize) {
        // 分页查询(紧随跟在其后的第一条查询sql将会被分页)
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> notices = noticeDao.selectAllNoticeDesc();
        PageInfo noticePageInfo = new PageInfo(notices);
        PageInfo<NoticeVO> pageInfo = new PageInfo<>(BeanCopyUtils.copyList(notices, NoticeVO.class));
        pageInfo.setTotal(noticePageInfo.getTotal());
        return CommonResult.success(pageInfo);
    }
}
