package com.cglee079.podo.structure.gateway.security.filter;

import com.cglee079.podo.structure.core.vo.JwtValue;
import com.cglee079.podo.structure.gateway.response.ResponseCode;
import com.cglee079.podo.structure.gateway.util.RequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
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

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        final ServerHttpRequest request = exchange.getRequest();

        if(HttpMethod.OPTIONS.equals(request.getMethod())){
            return chain.filter(exchange);
        }

        log.info("사용자 인증을 수행합니다");

        try {

            final String token = RequestUtil.getAuthToken(request);

            if(StringUtils.isEmpty(token)){
                return chain.filter(exchange);
            }

            final JwtValue JwtValue = readJson(authenticate(token));

            if (Objects.isNull(JwtValue)) {
                return chain.filter(exchange);
            }

            final List<SimpleGrantedAuthority> roles = JwtValue.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(toList());

            return chain.filter(exchange).subscriberContext(ReactiveSecurityContextHolder.withAuthentication(new UsernamePasswordAuthenticationToken(JwtValue, null, roles)));

        } catch (ExpiredJwtException exception) {
            final JSONObject response = new JSONObject();

            response.put("errorCode", ResponseCode.EXPIRE_TOKEN.getCode());
            response.put("url", "/refresh");

            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, response.toString()));
        }

    }

    public String authenticate(String token) {
        try {

            return Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        } catch (ExpiredJwtException exception) {
            final JSONObject response = new JSONObject();

            response.put("errorCode", ResponseCode.EXPIRE_TOKEN.getCode());
            response.put("url", "/refresh");

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, response.toString());
        }


    }

    private JwtValue readJson(String tokenValueJson) {
        try {
            return new ObjectMapper().readValue(tokenValueJson, JwtValue.class);
        } catch (JsonProcessingException e) {
            log.error("", e);
            return null;
        }
    }

}


