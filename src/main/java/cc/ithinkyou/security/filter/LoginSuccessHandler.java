package cc.ithinkyou.security.filter;


import cc.ithinkyou.config.ApiConfig;
import cc.ithinkyou.utils.HttpUtils;
import cc.ithinkyou.config.CacheConst;
import cc.ithinkyou.config.ErrCode;
import cc.ithinkyou.mapper.UserMapper;
import cc.ithinkyou.model.Result;
import cc.ithinkyou.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * 登录成功 处理事件
 *
 * @author GL
 * @created 2018/3/12.
 */
@Slf4j
@Component
public class LoginSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MemcachedClient cache;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();

        String token = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + CacheConst.TIME_OUT_ONE_DAY * 1000))
                .signWith(SignatureAlgorithm.HS512, ApiConfig.JWT_KEY) //采用HS512算法
                .compact();

        webFilterExchange.getExchange().getResponse().getHeaders().add("Authorization", token);
        cache.set(String.format(CacheConst.TOKEN_KEY, authentication.getName()), CacheConst.TIME_OUT_ONE_DAY, token);

        User user = userMapper.selectByName(authentication.getName());
        if (user != null) {
            return HttpUtils.render(exchange, new Result(user));
        }
        return HttpUtils.render(exchange, new Result(ErrCode.USER_NOT_FOUND, null));
    }
}
