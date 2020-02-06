package com.cglee079.podo.structure.gateway.security.filter;

import com.cglee079.podo.structure.gateway.security.BlacklistStore;
import com.cglee079.podo.structure.gateway.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BlacklistFilter implements WebFilter {

    private final BlacklistStore blacklistStore;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        if(!blacklistStore.valid(RequestUtil.getAuthToken(exchange.getRequest()))){
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalidate accessToken"));
        }

        return chain.filter(exchange);
    }
}
