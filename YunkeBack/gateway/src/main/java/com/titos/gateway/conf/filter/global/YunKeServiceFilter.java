package com.titos.gateway.conf.filter.global;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Titos
 */
@Slf4j
public class YunKeServiceFilter implements GlobalFilter, Ordered {
    private static final String TIME = "Time";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long start = exchange.getAttribute(TIME);
                    if (start != null) {
                        log.info("exchange request uri: " + exchange.getRequest().getURI() + ", Time: " + (System.currentTimeMillis() - start) + "ms");
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
