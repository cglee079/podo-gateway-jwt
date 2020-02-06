package com.cglee079.podo.structure.gateway.security;

import com.cglee079.podo.structure.core.vo.Role;
import com.cglee079.podo.structure.gateway.security.filter.AuthFilter;
import com.cglee079.podo.structure.gateway.security.filter.BlacklistFilter;
import com.cglee079.podo.structure.gateway.security.store.InMemoryBlacklistStore;
import com.cglee079.podo.structure.gateway.security.store.RedisBlacklistStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final BlacklistFilter blacklistFilter;
    private final AuthFilter authFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity security) {
        security.cors().disable();
        security.csrf().disable();
        security.logout().disable();
        security.httpBasic().disable();
        security.formLogin().disable();

        security.addFilterBefore(blacklistFilter, SecurityWebFiltersOrder.CORS);
        security.addFilterBefore(authFilter, SecurityWebFiltersOrder.CORS);

        security.authorizeExchange().pathMatchers("/join").permitAll();
        security.authorizeExchange().pathMatchers("/login").permitAll();
        security.authorizeExchange().pathMatchers("/logout").permitAll();
        security.authorizeExchange().pathMatchers("/refresh").permitAll();

        security.authorizeExchange().pathMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        security.authorizeExchange().pathMatchers("/**").hasRole(Role.MEMBER.toString());

        return security.build();
    }

}
