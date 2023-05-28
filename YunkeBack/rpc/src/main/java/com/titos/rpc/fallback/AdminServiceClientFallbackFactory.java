package com.titos.rpc.fallback;

import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.rpc.clients.AdminServiceClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminServiceClientFallbackFactory implements FallbackFactory<AdminServiceClient> {
    @Override
    public AdminServiceClient create(Throwable throwable) {
        return new AdminServiceClient() {
            @Override
            public CommonResult getCaptchaEnabled() {
                return generalProcess();
            }

            @Override
            public CommonResult getEmailValidateEnabled() {
                return generalProcess();
            }

            @Override
            public CommonResult getBlockUsernameList() {
                return generalProcess();
            }

            /**
             * 通用的处理方法
             * @return
             */
            private CommonResult generalProcess() {
                return new CommonResult(StatusEnum.MICROSERVICE_ERROR.getCode(), StatusEnum.MICROSERVICE_ERROR.getMsg());
            }
        };
    }
}
