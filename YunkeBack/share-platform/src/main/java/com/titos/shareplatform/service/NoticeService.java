package com.titos.shareplatform.service;

import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.shareplatform.vo.NoticeVO;

import java.util.List;

public interface NoticeService {
    /**
     * 查询通告
     * @param pageNum 当前页
     * @param pageSize 每页的数量
     * @return 通告列表
     */
    CommonResult<PageInfo<NoticeVO>> listNotice(Integer pageNum, Integer pageSize);
}
