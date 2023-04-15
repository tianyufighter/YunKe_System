package com.titos.rpc.clients;

import com.titos.info.global.CommonResult;
import com.titos.rpc.fallback.AdminServiceClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "admin", fallbackFactory = AdminServiceClientFallbackFactory.class)
public interface AdminServiceClient {
    @GetMapping("/admin/config/isCaptchaEnabled")
    CommonResult getCaptchaEnabled();

    @GetMapping("/admin/config/isEmailValidateEnabled")
    CommonResult getEmailValidateEnabled();

    @GetMapping("/admin/config/blockUsernameList")
    CommonResult getBlockUsernameList();
}
