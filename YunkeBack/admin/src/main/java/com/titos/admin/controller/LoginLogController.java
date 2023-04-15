package com.titos.admin.controller;

import com.github.pagehelper.PageInfo;
import com.titos.info.log.model.LoginLog;
import com.titos.info.log.vo.LoginLogVO;
import com.titos.info.global.CommonResult;
import com.titos.rpc.clients.NormalServiceClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统访问记录
 * @author Titos
 */
@RestController
@RequestMapping("/admin/loginLog")
public class LoginLogController {
    @Resource
    private NormalServiceClient normalServiceClient;

    @GetMapping("/list")
    public CommonResult<PageInfo<LoginLog>> list(LoginLogVO loginLogVO) {
        return normalServiceClient.loginLogList(loginLogVO);
    }
    @PostMapping("/deleteBatch")
    public CommonResult remove(@RequestBody Map<String, Object> requestMap) {
        return normalServiceClient.loginLogRemove(requestMap);
    }
    @GetMapping("/clean")
    public CommonResult clean() {
        return normalServiceClient.loginLogClean();
    }
}
