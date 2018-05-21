package com.ithinkyou.cc.security;

import com.ithinkyou.cc.config.ApiConfig;
import com.ithinkyou.cc.config.CacheConst;
import io.jsonwebtoken.Jwts;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 上下文处理
 * @author GL
 */
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    MemcachedClient cache;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        Authentication authentication = context.getAuthentication();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add( new SimpleGrantedAuthority("ROLE_ADMIN") );
        authentication = new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getAuthorities(), authorities);

        context.setAuthentication(authentication);
        cache.set(String.format(CacheConst.SESSION_ID, context.getAuthentication().getName()), CacheConst.TIME_OUT_ONE_DAY, context);

        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {

        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token != null) {
            String username = Jwts.parser()
                    .setSigningKey(ApiConfig.JWT_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            String _token = (String) cache.get(String.format(CacheConst.TOKEN_KEY, username));
            if (_token == null || !token.equals(_token)) {
                return Mono.empty();
            }
            SecurityContext context = (SecurityContext) cache.get(String.format(CacheConst.SESSION_ID, username));
            return Mono.justOrEmpty(context);
        }
        return Mono.empty();
    }
}