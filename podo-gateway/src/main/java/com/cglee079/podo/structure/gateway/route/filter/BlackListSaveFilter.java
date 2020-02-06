package com.cglee079.podo.structure.gateway.route.filter;

import com.cglee079.podo.structure.gateway.security.BlacklistStore;
import com.cglee079.podo.structure.gateway.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(10000)
@RequiredArgsConstructor
@Component
public class BlackListSaveFilter implements GatewayFilter {

    private final BlacklistStore blacklistStore;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final String token = RequestUtil.getAuthToken(exchange.getRequest());

        blacklistStore.add(token);

        return chain.filter(exchange);
    }
}
