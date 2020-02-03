package com.cglee079.podo.structure.gateway.filter;

import com.cglee079.podo.structure.core.vo.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final AuthFilter authFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity security) {
        security.csrf().disable();
        security.logout().disable();
        security.httpBasic().disable();
        security.formLogin().disable();
        security.exceptionHandling();
        security.addFilterBefore(authFilter, SecurityWebFiltersOrder.HTTP_BASIC);

        security.authorizeExchange().pathMatchers(HttpMethod.POST, "/join/**").permitAll();
        security.authorizeExchange().pathMatchers(HttpMethod.POST, "/login/**").permitAll();
        security.authorizeExchange().pathMatchers("/**").hasRole(Role.MEMBER.toString());

        return security.build();
    }

}
