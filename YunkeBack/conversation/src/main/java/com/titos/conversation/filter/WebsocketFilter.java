package com.titos.conversation.filter;

import com.titos.tool.token.CustomStatement;
import com.titos.tool.token.TokenUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/8 13:34
 * @Version: 1.0.0
 * @Description:
 */
@WebFilter(filterName = "WebsocketFilter", urlPatterns = "/conversation/chat/*")
public class WebsocketFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = ((HttpServletRequest) servletRequest).getHeader("Sec-WebSocket-Protocol");
        response.setHeader("Sec-WebSocket-Protocol",token);
        CustomStatement customStatement = TokenUtil.getMsgFromToken(token, "YUNKE");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
