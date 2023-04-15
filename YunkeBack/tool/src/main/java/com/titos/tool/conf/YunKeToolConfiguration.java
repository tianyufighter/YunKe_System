package com.titos.tool.conf;

import com.titos.tool.token.TokenUtil;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Titos
 */
@ComponentScan("com.titos.tool")
@EnableAspectJAutoProxy
public class YunKeToolConfiguration {
    @Bean
    public RequestInterceptor headerInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes =  (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            if (TokenUtil.isTokenExisted(request)) {
                String token = TokenUtil.getToken(request);
                requestTemplate.header(TokenUtil.TOKEN_HEADER, token);
            }
        };
    }
}
