package com.ithinkyou.cc.utils;

import com.alibaba.fastjson.JSON;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;

/**
 * Http 工具类
 *
 * @author liguilong
 * @created 2018/2/25.
 */
public class HttpUtils {


    /**
     * 创建 Web Client
     *
     * @return
     * @throws SSLException
     */
    public static WebClient createWebClient() throws SSLException {
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        ClientHttpConnector httpConnector = new ReactorClientHttpConnector(opt -> opt.sslContext(sslContext));
        return WebClient.builder().clientConnector(httpConnector).baseUrl("https://wx.tpms.e5ex.com").build();
    }


    /**
     * 返回结果
     *
     * @param exchange
     * @param obj
     * @return
     */
    public static Mono<Void> render(ServerWebExchange exchange, Object obj) {
        ServerHttpResponse result = exchange.getResponse();
        result.setStatusCode(HttpStatus.OK);
        result.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();

        Mono<DataBuffer> bufferMono = Mono.just(bufferFactory.wrap(JSON.toJSONBytes(obj)));
        return result.writeWith(bufferMono);
    }

    /**
     * 设置Cors参数
     *
     * @param exchange
     */
    public static void setCorsParams(ServerWebExchange exchange) {
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        String origin = exchange.getRequest().getHeaders().getOrigin();
        if (origin != null) {
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", origin);
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "true");
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "POST, GET");
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.getResponse().getHeaders().add("Access-Control-Expose-Headers", "Authorization");
            exchange.getResponse().getHeaders().add("Access-Control-Max-Age", "3600");
        }
    }
}
