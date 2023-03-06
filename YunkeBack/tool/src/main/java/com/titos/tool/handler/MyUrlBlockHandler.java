package com.titos.tool.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.titos.info.global.CommonResult;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName MyUrlBlockHandler
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/14 22:06
 **/
@Component
public class MyUrlBlockHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException ex) throws Exception {
        CommonResult<String> errorMsg = null;
        if (ex instanceof FlowException) {
            // 流控异常
            errorMsg = CommonResult.fail(100,"流控异常");

        } else if (ex instanceof DegradeException) {
            // 降级异常
            errorMsg = CommonResult.fail(100,"降级异常");

        } else if (ex instanceof ParamFlowException) {
            // 热点异常
            errorMsg = CommonResult.fail(100,"热点异常");

        } else if (ex instanceof SystemBlockException) {
            // 系统异常
            errorMsg = CommonResult.fail(100,"系统异常");

        } else if (ex instanceof AuthorityException) {
            // 授权异常
            errorMsg = CommonResult.fail(100,"授权异常");
        }
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        new ObjectMapper()
                .writeValue(
                        httpServletResponse.getWriter(),
                        errorMsg
                );
    }

}
