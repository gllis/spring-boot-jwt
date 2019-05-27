package cc.ithinkyou.security;

import cc.ithinkyou.model.User;
import cc.ithinkyou.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户鉴权
 *
 * @author GL
 * @created 2018/05/21
 */
@Slf4j
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    UserService userService;


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {


        return Mono.just(authentication)
                .publishOn(Schedulers.parallel())
                .flatMap(token -> {
                    try {
                        log.info("开始查询用户信息");
                        User user = userService.getByName(token.getName());
                        String password = DigestUtils.md5DigestAsHex(token.getCredentials().toString().getBytes());
                        if (user != null && password.equals(user.getPassword())) {
                            List<GrantedAuthority> authorities = new ArrayList<>();
                            authorities.add(new SimpleGrantedAuthority(user.getRole()));
                            Authentication auth = new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), authorities);
                            return Mono.justOrEmpty(auth);
                        } else {
                            return Mono.error(new BadCredentialsException("Invalid Credentials"));
                        }

                    } catch (Throwable error) {
                        return Mono.error(new BadCredentialsException("Invalid Credentials"));
                    }
                })
                .filter(auth -> auth.isAuthenticated());

    }
}