package com.titos.shareplatform.controller;

import com.titos.info.global.CommonResult;
import com.titos.info.shareplatform.vo.NoticeVO;
import com.titos.shareplatform.service.NoticeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName NoticeController
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 17:57
 **/
@RestController
@RequestMapping("/notice")
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
    public CommonResult<List<NoticeVO>> listNotice(
            @RequestParam(defaultValue = "1", value = "pageNum") Long pageNum,
            @RequestParam(defaultValue = "3", value = "pageSize") Long pageSize) {
        return noticeService.listNotice(pageNum, pageSize);
    }



}
