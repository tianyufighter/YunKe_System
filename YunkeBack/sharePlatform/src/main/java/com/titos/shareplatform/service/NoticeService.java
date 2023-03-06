package com.titos.shareplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.titos.info.global.CommonResult;
import com.titos.info.shareplatform.entity.Notice;
import com.titos.info.shareplatform.vo.NoticeVO;

import java.util.List;

/**
 * @ClassName NoticeService
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 18:00
 **/
public interface NoticeService extends IService<Notice> {
    /**
     * 查询通告
     *
     * @param pageNum  当前页
     * @param pageSize 每页的数量
     * @return 通告列表
     */
    CommonResult<List<NoticeVO>> listNotice(Long pageNum, Long pageSize);
}
