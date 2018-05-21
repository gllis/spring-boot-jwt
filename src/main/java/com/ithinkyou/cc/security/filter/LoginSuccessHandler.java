package com.ithinkyou.cc.security.filter;


import com.ithinkyou.cc.config.ApiConfig;
import com.ithinkyou.cc.config.CacheConst;
import com.ithinkyou.cc.config.ErrCode;
import com.ithinkyou.cc.mapper.UserMapper;
import com.ithinkyou.cc.model.Result;
import com.ithinkyou.cc.model.User;
import com.ithinkyou.cc.utils.HttpUtils;
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
        cache.set(String.format(CacheConst.TOKEN_KEY, authentication.getName()), (int)CacheConst.TIME_OUT_ONE_DAY, token);

        User user = userMapper.selectUser(authentication.getName());
        if (user != null) {
            return HttpUtils.render(exchange, new Result(user));
        }
        return HttpUtils.render(exchange, new Result(ErrCode.USER_NOT_FOUND, null));
    }
}
