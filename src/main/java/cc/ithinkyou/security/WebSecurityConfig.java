package cc.ithinkyou.security;

import cc.ithinkyou.security.filter.LoginFailHandler;
import cc.ithinkyou.security.filter.CorsFilter;
import cc.ithinkyou.security.filter.LoginSuccessHandler;
import cc.ithinkyou.security.filter.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
/**
 * SpringSecurity的配置
 * 通过SpringSecurity的配置，将JWTLoginHandler，JWTAuthenticationFilter组合在一起
 *
 * @author GL on 2018/02/23.
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LoginFailHandler loginFailHandler;

    @Autowired
    SecurityContextRepository securityContextRepository;

    @Autowired
    UnauthorizedEntryPoint unauthorizedEntryPoint;


    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
                .authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .authenticationManager(authenticationManager)
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
                .and()
                .formLogin().loginPage("/login")
                .authenticationSuccessHandler(loginSuccessHandler)
                .authenticationFailureHandler(loginFailHandler)
                .and()
                .securityContextRepository(securityContextRepository)
                .addFilterAt(new CorsFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }


}
