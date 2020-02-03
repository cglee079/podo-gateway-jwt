package com.cglee079.podo.structure.gateway.filter;

import com.cglee079.podo.structure.core.vo.TokenValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class AuthFilter implements WebFilter {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("사용자 인증을 수행합니다");

        final HttpHeaders headers = exchange.getRequest().getHeaders();

        final List<String> authorizations = headers.get(HEADER_STRING);
        if (Objects.isNull(authorizations) || authorizations.isEmpty()) {
            return chain.filter(exchange);
        }

        final String authValue = authorizations.get(0);
        final TokenValue tokenValue = readJson(authenticate(authValue));

        if (Objects.isNull(tokenValue)) {
            return chain.filter(exchange);
        }

        final List<SimpleGrantedAuthority> roles = tokenValue.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(toList());

        return chain.filter(exchange).subscriberContext(ReactiveSecurityContextHolder.withAuthentication(new UsernamePasswordAuthenticationToken(tokenValue, null, roles)));

    }

    public String authenticate(String authHeaderValue) {
        log.info("사용자 인증을 시작합니다, {}", authHeaderValue);

        final String token = authHeaderValue.replace(TOKEN_PREFIX, "").trim();

        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    private TokenValue readJson(String tokenValueJson) {
        try {
            return new ObjectMapper().readValue(tokenValueJson, TokenValue.class);
        } catch (JsonProcessingException e) {
            log.error("", e);
            return null;
        }
    }

}


