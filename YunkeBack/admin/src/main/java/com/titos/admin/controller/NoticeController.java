package com.titos.admin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.titos.admin.convert.NoticeConvert;
import com.titos.admin.model.Notice;
import com.titos.admin.service.NoticeService;
import com.titos.admin.vo.NoticeVO;
import com.titos.info.global.CommonResult;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告 信息操作处理
 */
@RestController
@RequestMapping("/admin/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    /**
     * 获取通知公告列表
     * @param noticeVO
     * @return
     */
    @GetMapping("/list")
    public CommonResult<PageInfo<Notice>> list(NoticeVO noticeVO) {
        PageHelper.startPage(noticeVO.getPageNum(), noticeVO.getPageSize());
        List<Notice> list = noticeService.selectNoticeList(NoticeConvert.toNoticeByNoticeVO(noticeVO));
        return CommonResult.success(new PageInfo<>(list), "查询成功");
    }

    /**
     * 根据通知公告编号获取详细信息
     * @param noticeId
     * @return
     */
    @GetMapping("/getNoticeById")
    public CommonResult getInfo(Integer noticeId) {
        return CommonResult.success(noticeService.selectNoticeById(noticeId));
    }

    /**
     * 新增通知公告
     * @param notice
     * @return
     */
    @InjectToken
    @PostMapping("/addNotice")
    public CommonResult add(CustomStatement customStatement,@RequestBody Notice notice) {
        notice.setUserId(customStatement.getId());
        Integer res = noticeService.insertNotice(notice);
        if (res != 1) {
            return CommonResult.fail();
        } else {
            return CommonResult.success();
        }
    }

    /**
     * 修改通知公告
     * @param notice
     * @return
     */
    @PostMapping("/updateNotice")
    public CommonResult updateNotice(@RequestBody Notice notice) {
        Integer res = noticeService.updateNotice(notice);
        if (res == 1) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }

    /**
     * 根据通知的id删除对应的通知信息
     * @param noticeId
     * @return
     */
    @GetMapping("/deleteNotice")
    public CommonResult remove(Integer noticeId) {
        Integer res = noticeService.deleteNoticeById(noticeId);
        if (res == 1) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }
}
