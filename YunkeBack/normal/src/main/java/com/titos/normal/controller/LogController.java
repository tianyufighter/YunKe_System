package com.titos.normal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.log.convert.LoginLogConvert;
import com.titos.info.log.model.LoginLog;
import com.titos.info.log.vo.LoginLogVO;
import com.titos.normal.service.log.LoginLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 日志操作 controller层
 */
@RestController
@RequestMapping("/common/log")
public class LogController {
    @Resource
    private LoginLogService loginLogService;
    @PostMapping("/loginLog/list")
    public CommonResult<PageInfo<LoginLog>> loginLogList(@RequestBody LoginLogVO loginLogVO) {
        PageHelper.startPage(loginLogVO.getPageNum(), loginLogVO.getPageSize());
        List<LoginLog> list = loginLogService.selectLoginLogList(LoginLogConvert.toLoginLogByLoginLogVO(loginLogVO));
        return CommonResult.success(new PageInfo<>(list));
    }
    @PostMapping("/loginLog/deleteBatch")
    public CommonResult loginLogRemove(@RequestBody Map<String, Object> requestMap) {
        ObjectMapper mapper = new ObjectMapper();
        Integer[] ids = mapper.convertValue(requestMap.get("ids"), Integer[].class);
        return CommonResult.success(loginLogService.deleteLoginLogByIds(ids));
    }
    @GetMapping("/loginLog/clean")
    public CommonResult loginLogClean() {
        loginLogService.cleanLoginLog();
        return CommonResult.success();
    }

    @PostMapping("/loginlog/addLoginLog")
    public CommonResult addLoginLog(@RequestBody LoginLog loginLog) {
        loginLogService.insertLoginLog(loginLog);
        return CommonResult.success();
    }
}
