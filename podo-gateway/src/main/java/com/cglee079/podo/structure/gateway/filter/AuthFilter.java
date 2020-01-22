package com.cglee079.podo.structure.gateway.filter;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthFilter implements GatewayFilter{

    private static final String AUTH_SERVER_URL = "http://localhost:7070/auth";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Auth 필터를 실행합니다 ------- ");

        final HttpHeaders headers = exchange.getRequest().getHeaders();
        final HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        final ResponseEntity<String> authResponse = new RestTemplate().exchange(AUTH_SERVER_URL, HttpMethod.POST, httpEntity, String.class);

        log.info("Response Code {}", authResponse.getStatusCode());

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(authResponse.getStatusCode());

        return chain.filter(exchange);

    }

}


