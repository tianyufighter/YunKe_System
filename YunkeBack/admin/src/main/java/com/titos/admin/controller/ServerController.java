package com.titos.admin.controller;

import com.titos.admin.model.server.Server;
import com.titos.info.global.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 * @author Titos
 */
@RestController
@RequestMapping("/admin/server")
public class ServerController {
    @GetMapping
    public CommonResult getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return CommonResult.success(server);
    }
}
