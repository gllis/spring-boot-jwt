package cc.ithinkyou.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Http 工具类
 *
 * @author GL
 * @created 2018/2/25.
 */
public class HttpUtils {


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
