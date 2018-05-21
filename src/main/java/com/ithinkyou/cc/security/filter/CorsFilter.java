package com.ithinkyou.cc.security.filter;

import com.ithinkyou.cc.utils.HttpUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 跨域过滤器
 *
 * @author GL
 * @created 2018/3/15.
 */
public class CorsFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpUtils.setCorsParams(exchange);
        return chain.filter(exchange);
    }
}
