package com.cglee079.podo.structure.gateway.route;

import com.cglee079.podo.structure.gateway.route.filter.BlackListSaveFilter;
import com.cglee079.podo.structure.gateway.security.filter.AuthFilter;
import com.cglee079.podo.structure.gateway.security.filter.BlacklistFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {

    @Bean
    public RouteLocator joinRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .order(-1)
                        .path("/join")
                        .uri("http://localhost:6061")
                        .id("external-member")
                )
                .build();
    }


    @Bean
    public RouteLocator serverRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/**")
                        .uri("http://localhost:8080")
                        .id("resource-server")
                )
                .build();
    }
}
