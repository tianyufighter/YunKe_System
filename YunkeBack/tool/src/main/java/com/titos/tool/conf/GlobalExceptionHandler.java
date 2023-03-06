package com.titos.tool.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.tool.exception.JwtExpireException;
import com.titos.tool.exception.JwtNotExistException;
import com.titos.tool.exception.ParameterException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 全局异常处理
 * @author Titos
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(JwtNotExistException.class)
    public CommonResult jwtNotExistExceptionHandler(JwtNotExistException e) {
        String msg = getMsgFromException(e, "未登录");
        return new CommonResult(StatusEnum.UN_LOGIN.getCode(), msg);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(JwtExpireException.class)
    public CommonResult jwtExpiredExceptionHandler(JwtExpireException e) {
        String msg = getMsgFromException(e, "用户登录状态过期");
        return new CommonResult(StatusEnum.TOKEN_EXPIRED.getCode(), msg);
    }
    /**
     * 参数错误的最高拦截器
     * BindException是MVC在注入参数时发生的错误
     * ParameterException是本系统自定的错误
     * @param e
     * @return
     */
    @ExceptionHandler({ParameterException.class, BindException.class})
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CommonResult ParameterExceptionHandler(ParameterException e){
        String msg = getMsgFromException(e, "参数错误");
        return new CommonResult(StatusEnum.PARAM_ERROR.getCode(), msg);
    }

    /**
     * ===============================================================
     * =                    系统错误拦截模块                          =
     * ===============================================================
     */
    /**
     * 所有异常的最高级拦截器
     * 被该拦截器捕捉，将打印异常信息
     * 本拦截器不会进行log模块的提交，所以请使用注解来开启提交
     * @param e
     * @return
     */
    @ExceptionHandler(FeignException.class)
    public CommonResult feignExceptionHandler(FeignException e, HttpServletResponse httpServletResponse) {
        LOGGER.error("==============微服务调用异常==============");
        String req = e.hasRequest() ? e.request().toString() : "无请求";
        LOGGER.error("调用接口:{}",req);
        LOGGER.error("响应状态:{}",e.status());

        StatusEnum statusEnum = null;
        String msg = null;
        if (!e.responseBody().isPresent()) {
            statusEnum=StatusEnum.ERROR;
            msg = "服务器错误";
        } else {
            ByteBuffer byteBuffer = e.responseBody().get();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectReader reader = objectMapper.readerFor(CommonResult.class);
            try {
                CommonResult baseVo = reader.readValue(byteBuffer.array());
                statusEnum = StatusEnum.valueOf(baseVo.getCode());
                msg = baseVo.getMessage();
                LOGGER.error("响应msg:{}",msg);
                LOGGER.error("响应Body:{}",baseVo.toString());
            } catch (IOException ioException) {
                statusEnum=StatusEnum.ERROR;
                msg = "服务器错误";
                LOGGER.error("JSON解析错误,UTF-8格式JSON:\n{}", Charset.forName("utf-8").decode(byteBuffer).toString());
            }
        }

        printErr(e);
        httpServletResponse.setStatus(e.status());

        return new CommonResult(statusEnum.getCode(),msg);
    }
    /**
     * 所有异常的最高级拦截器
     * 被该拦截器捕捉，将打印异常信息
     * 本拦截器不会进行log模块的提交，所以请使用注解来开启提交
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResult exceptionHandler(Exception e) {
        LOGGER.error("==============发生异常==============");
        printErr(e);
        return new CommonResult(StatusEnum.ERROR.getCode(),StatusEnum.ERROR.getMsg());
    }

    private void printErr(Exception e) {
        LOGGER.error("异常类型:{}", e.getClass());
        LOGGER.error("异常消息:{}", e.getMessage());
        LOGGER.error("异常具体内容:");
        LOGGER.error("---------------------------------------");
        e.printStackTrace();
        LOGGER.error("---------------------------------------");
        LOGGER.error("===================================");
    }

    /**
     * 从异常中获取msg，若没有msg或为空，则使用默认
     * @param e
     * @param defaultMsg
     * @return
     */
    private String getMsgFromException(Exception e, String defaultMsg) {
        return e.getMessage().isEmpty() ? defaultMsg : e.getMessage();
    }
}
