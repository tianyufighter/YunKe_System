package com.titos.admin.controller;

import com.titos.info.global.CommonResult;
import com.titos.rpc.clients.NormalServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 敏感词过滤管理 controller层
 * @author Titos
 */
@RestController
@RequestMapping("/admin/sensitive")
public class SensitiveController {
    @Resource
    private NormalServiceClient normalServiceClient;
    @GetMapping("/refresh")
    public CommonResult refresh() {
        return normalServiceClient.refresh();
    }
}
