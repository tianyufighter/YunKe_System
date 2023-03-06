package com.titos.shareplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titos.info.global.CommonResult;
import com.titos.info.shareplatform.entity.Notice;
import com.titos.info.shareplatform.vo.NoticeVO;
import com.titos.shareplatform.dao.NoticeDao;
import com.titos.shareplatform.service.NoticeService;
import com.titos.tool.BeanCopyUtils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName NoticeServiceImpl
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 18:01
 **/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, Notice> implements NoticeService {

    @Resource
    private NoticeDao noticeDao;

    @Override
    public CommonResult<List<NoticeVO>> listNotice(Long pageNum, Long pageSize) {
        Page<Notice> noticePage = noticeDao.selectPage(new Page<>(pageNum, pageSize), new LambdaQueryWrapper<Notice>()
                .select(Notice::getId, Notice::getNoticeContent)
                .orderByDesc(Notice::getCreateTime));
        return CommonResult.success(BeanCopyUtils.copyList(noticePage.getRecords(), NoticeVO.class));
    }
}
