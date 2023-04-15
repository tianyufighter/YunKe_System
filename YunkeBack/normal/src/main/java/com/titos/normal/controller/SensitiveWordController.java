package com.titos.normal.controller;

import com.github.houbb.sensitive.word.core.SensitiveWordHelper;
import com.titos.info.global.CommonResult;
import com.titos.normal.service.sensitive.impl.SensitiveWordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/common/sensitive")
public class SensitiveWordController {
    @Resource
    private SensitiveWordService sensitiveWordService;
    @GetMapping("/refresh")
    public CommonResult refresh() {
        sensitiveWordService.refresh();
        return CommonResult.success();
    }

    /**
     * 默认的替换策略
     * @param text
     * @return
     */
    @GetMapping("/replace")
    public CommonResult replaceContent(@RequestParam("text") String text) {
        String result = SensitiveWordHelper.replace(text);
        return CommonResult.success(result);
    }
}
