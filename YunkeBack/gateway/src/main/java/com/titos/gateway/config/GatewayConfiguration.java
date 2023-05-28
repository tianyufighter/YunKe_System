package com.titos.gateway.config;


import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import lombok.SneakyThrows;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * 在 Sentinel 控制台配置规后，服务出现限流或降级时，我们需要服务端返回友好的异常信息，而不是一个简单的错误页面。
 * 在上篇文章中介绍了自定义异常处理器，即实现 BlockExceptionHandler 接口来完成功能。但是，Gateway 整合 Sentienl 后，该方案就失效了。
 * 我们需要配置 BlockRequestHandler 实例。
 */
@Configuration
public class GatewayConfiguration {
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean(name = "myBlockRequestHandler")
    public BlockRequestHandler myBlockRequestHandler() {
        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            @SneakyThrows
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                CommonResult<String> errorMsg = null;
                if (throwable instanceof FlowException) {
                    // 流控异常
                    errorMsg = CommonResult.fail(StatusEnum.SENTINEL_FLOW_EXCEPTION.getCode(),StatusEnum.SENTINEL_FLOW_EXCEPTION.getMsg());
                } else if (throwable instanceof DegradeException) {
                    // 降级异常
                    errorMsg = CommonResult.fail(StatusEnum.SENTINEL_DEGRADE_EXCEPTION.getCode(),StatusEnum.SENTINEL_DEGRADE_EXCEPTION.getMsg());
                } else if (throwable instanceof ParamFlowException) {
                    // 热点异常
                    errorMsg = CommonResult.fail(StatusEnum.SENTINEL_PARAMFLOW_EXCEPTION.getCode(),StatusEnum.SENTINEL_PARAMFLOW_EXCEPTION.getMsg());

                } else if (throwable instanceof SystemBlockException) {
                    // 系统异常
                    errorMsg = CommonResult.fail(StatusEnum.SENTINEL_SYSTEM_EXCEPTION.getCode(),StatusEnum.SENTINEL_SYSTEM_EXCEPTION.getMsg());

                } else if (throwable instanceof AuthorityException) {
                    // 授权异常
                    errorMsg = CommonResult.fail(StatusEnum.SENTINEL_AUTHORITY_EXCEPTION.getCode(),StatusEnum.SENTINEL_AUTHORITY_EXCEPTION.getMsg());
                } else {
                    errorMsg = CommonResult.fail(StatusEnum.SENTINEL_UNKNOWN_EXCEPTION.getCode(), StatusEnum.SENTINEL_UNKNOWN_EXCEPTION.getMsg());
                }

                return ServerResponse.status(HttpStatus.BAD_GATEWAY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(new ObjectMapper().writeValueAsString(errorMsg)));
            }
        };
        return blockRequestHandler;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler(BlockRequestHandler myBlockRequestHandler) {

        //重定向bloack处理
        //GatewayCallbackManager.setBlockHandler(new RedirectBlockRequestHandler("https://www.extlight.com"));

        //自定义bloack处理
        GatewayCallbackManager.setBlockHandler(myBlockRequestHandler);
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }
}
