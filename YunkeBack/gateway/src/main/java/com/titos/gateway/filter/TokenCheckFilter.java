package com.titos.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.titos.info.exception.JwtExpireException;
import com.titos.info.exception.JwtNotExistException;
import com.titos.info.exception.JwtVerifyException;
import com.titos.info.global.CommonResult;
import com.titos.info.global.constant.CacheConstants;
import com.titos.info.global.enums.StatusEnum;
import com.titos.tool.cache.redis.RedisCache;
import com.titos.tool.token.TokenSettings;
import com.titos.tool.token.TokenUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

/**
 * 使用网关进行token校验拦截
 * @author Titos
 */
@Component
public class TokenCheckFilter implements GlobalFilter, Ordered {
    /**
     * 主要用于获取secretKey
     */
    @Resource
    private TokenSettings tokenSettings;

    @Resource
    private RedisCache redisCache;
    /**
     * 制定好放行的路径
     */
    public static final List<String> ALLOW_URL = Arrays.asList("/personManagement/signUp", "/personManagement/verifyEmail", "/personManagement/login", "/personManagement/verifyCode", "/personManagement/isCaptchaEnabled", "/admin/user/login", "/common/file/upload", "/personManagement/resetPassword", "/personManagement/verifyResetPassword");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /*
            一般和前端约定好 一般把token放在请求头里面，一般key为Authorization, value为token值
            拿请求url判断是否为登录url是就放行，不是继续操作，拿到请求头中token进行校验
         */
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();
        if (ALLOW_URL.contains(path) || path.contains("/personManagement/verifyEmail") || path.contains("/admin/druid")) {
            return chain.filter(exchange);
        }
        // 校验token
        HttpHeaders headers = request.getHeaders();
        List<String> authorization = headers.get("Authorization");
        if (!CollectionUtils.isEmpty(authorization)) {
            // 如果authorization集合不为空
            // 获取第一个为token
            String token = authorization.get(0);
            // 判断token是不是空字符串
            if (StringUtils.hasText(token)) {
                // 不为空，则校验token
                try {
                    TokenUtil.parse(token, tokenSettings.getTokenSecretKey());
                    // 如果token解析无误，那么就在redis中更新系统的在线人数
                    redisCache.setCacheObject(CacheConstants.ONLINE_PEOPLE_COUNT, TokenUtil.getOnlineCount());
                    // 如果验证通过则放行
                    return chain.filter(exchange);
                } catch (JwtExpireException jwtExpireException) {
                    return getVoidMono(response, StatusEnum.TOKEN_EXPIRED);
                } catch (JwtVerifyException jwtVerifyException) {
                    return getVoidMono(response, StatusEnum.TOKEN_ERROR);
                } catch (JwtNotExistException jwtNotExistException) {
                    return getVoidMono(response, StatusEnum.TOKEN_NULL);
                } catch (Exception e) {
                    e.printStackTrace();
                    return getVoidMono(response, StatusEnum.TOKEN_UNKNOWN);
                }
            } else {
                getVoidMono(response, StatusEnum.TOKEN_NULL);
            }
        }
        // 如果不符合就不给访问
        return getVoidMono(response, StatusEnum.TOKEN_UNKNOWN);
    }

    private Mono<Void> getVoidMono(ServerHttpResponse response, StatusEnum statusEnum) {
        response.getHeaders().set("content-type", "application/json;charset=utf-8");
        HashMap<String, Object> map = new HashMap<>(4);
        CommonResult commonResult = CommonResult.builder()
                .code(statusEnum.getCode())
                .message(statusEnum.getMsg())
                .build();
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(commonResult).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        // 配置优先级, 越小越先执行
        return -1;
    }
}
