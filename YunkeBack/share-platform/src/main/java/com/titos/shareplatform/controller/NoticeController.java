package com.titos.shareplatform.controller;

import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.shareplatform.service.NoticeService;
import com.titos.shareplatform.vo.NoticeVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sharePlatform/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * 查询通告
     *
     * @param pageNum  当前页
     * @param pageSize 每页的数量
     * @return 通告列表
     */
    @GetMapping(value = "/list")
    public CommonResult<PageInfo<NoticeVO>> listNotice(
            @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
            @RequestParam(defaultValue = "3", value = "pageSize") Integer pageSize) {
        return noticeService.listNotice(pageNum, pageSize);
    }

}